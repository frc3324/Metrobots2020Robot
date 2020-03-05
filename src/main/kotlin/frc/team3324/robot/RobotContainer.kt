package frc.team3324.robot

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.*
import edu.wpi.first.wpilibj.XboxController.Button
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.team3324.robot.autocommands.FinalAutoGroup
import frc.team3324.robot.autocommands.ShooterAndStorageFiveBalls
import frc.team3324.robot.autocommands.ShooterAndStorageParallel
import frc.team3324.robot.climber.Climber
import frc.team3324.robot.climber.commands.RunClimber
import frc.team3324.robot.drivetrain.DriveTrain
import frc.team3324.robot.drivetrain.commands.auto.RunDrivetrain
import frc.team3324.robot.drivetrain.commands.teleop.Drive
import frc.team3324.robot.drivetrain.commands.teleop.GyroTurn
import frc.team3324.robot.intake.Intake
import frc.team3324.robot.intake.Pivot
import frc.team3324.robot.intake.commands.PivotPID
import frc.team3324.robot.intake.commands.RunIntake
import frc.team3324.robot.intake.commands.RunIntakeConstant
import frc.team3324.robot.intake.commands.RunPivot
import frc.team3324.robot.shooter.Shooter
import frc.team3324.robot.shooter.commands.RunShooter
import frc.team3324.robot.shooter.commands.StopShooter
import frc.team3324.robot.storage.Storage
import frc.team3324.robot.storage.commands.RunStorage
import frc.team3324.robot.storage.commands.RunStorageConstant
import frc.team3324.robot.util.Camera
import frc.team3324.robot.util.Consts
import frc.team3324.robot.util.SwitchRelay
import frc.team3324.robot.util.ToggleLight
import io.github.oblarg.oblog.Logger

class RobotContainer {
    private val intake = Intake()
    private val storage = Storage()
    private val driveTrain = DriveTrain()
    private val climber = Climber()
    private val pivot = Pivot()
    private val shooter = Shooter()


    private val table = NetworkTableInstance.getDefault()
    private val cameraTable = table.getTable("chameleon-vision").getSubTable("usb")

    private val primaryController = XboxController(0)
    private val secondaryController = XboxController(1)
    private val bongos = Joystick(2)

    private val primaryRightX: Double
        get() = primaryController.getX(GenericHID.Hand.kLeft)
    private val primaryLeftY: Double
        get() = primaryController.getY(GenericHID.Hand.kRight)

    private val primaryTriggerRight: Double
        get() = primaryController.getTriggerAxis(GenericHID.Hand.kRight)
    private val primaryTriggerLeft: Double
        get() = primaryController.getTriggerAxis(GenericHID.Hand.kLeft)

    private val secondaryRightX: Double
        get() = secondaryController.getX(GenericHID.Hand.kLeft)
    private val secondRightY: Double
        get() = secondaryController.getY(GenericHID.Hand.kRight)
    private val secondLeftY: Double
        get() = secondaryController.getY(GenericHID.Hand.kLeft)

    private val secondTriggerRight: Double
        get() = secondaryController.getTriggerAxis(GenericHID.Hand.kRight)
    private val secondTriggerLeft: Double
        get() = secondaryController.getTriggerAxis(GenericHID.Hand.kLeft)




   init {
       Robot.light.set(true)
       Logger.configureLoggingAndConfig(this, true)
       Camera.schedule()
       driveTrain.defaultCommand = Drive(driveTrain, {primaryController.getY(GenericHID.Hand.kLeft)}, {primaryController.getX(GenericHID.Hand.kRight)})


       pivot.defaultCommand = RunPivot(pivot, -0.05)
       intake.defaultCommand = RunIntake(storage, intake, this::secondTriggerRight, this::secondTriggerLeft)
       configureButtonBindings()

   }

    fun configureButtonBindings() {
        JoystickButton(primaryController, Button.kBumperLeft.value).whileHeld(PivotPID(pivot, -90.0))
        JoystickButton(primaryController, Button.kBumperRight.value).whileHeld(PivotPID(pivot, 0.0))
        JoystickButton(primaryController, Button.kX.value).whenPressed(ToggleLight(Robot.light))
        JoystickButton(primaryController, Button.kA.value).whileHeld(RunClimber(climber, -1.0, {input: Double -> climber.leftSpeed = input}))
        JoystickButton(primaryController, Button.kB.value).whileHeld(RunClimber(climber, -1.0, {input: Double -> climber.rightSpeed = input}))
        JoystickButton(primaryController, Button.kStickLeft.value).whileHeld(RunClimber(climber, 1.0, {input: Double -> climber.leftSpeed = input}))
        JoystickButton(primaryController, Button.kStickRight.value).whileHeld(RunClimber(climber, 1.0, {input: Double -> climber.rightSpeed = input}))
        JoystickButton(primaryController, Button.kY.value).whileHeld(GyroTurn(
                driveTrain,
                1.0/80.0,
                Consts.DriveTrain.ksVolts/12,
                {cameraTable.getEntry("targetYaw").getDouble(0.0)},
                {input -> driveTrain.curvatureDrive(0.0, input, true)}
        ))
        JoystickButton(secondaryController, Button.kX.value).whenPressed(RunShooter(shooter, {cameraTable.getEntry("targetArea").getDouble(3800.0)}, false).withTimeout(10.0))
        JoystickButton(secondaryController, Button.kY.value).whenPressed(RunShooter(shooter, {cameraTable.getEntry("targetArea").getDouble(3800.0)}, true).withTimeout(10.0))
        JoystickButton(secondaryController, Button.kBumperLeft.value).whileHeld(RunStorageConstant(storage, 0.6))
        JoystickButton(secondaryController, Button.kBumperRight.value).whileHeld(RunStorageConstant(storage, -0.6))
        JoystickButton(secondaryController, Button.kA.value).whileHeld(RunIntakeConstant(storage, intake, pivot,-1.0))
        JoystickButton(secondaryController, Button.kB.value).whileHeld(RunIntakeConstant(storage, intake, pivot,1.0))
        JoystickButton(secondaryController, Button.kStart.value).whenPressed(StopShooter(shooter))
        JoystickButton(bongos, 1).whileHeld(RunStorageConstant(storage, 0.8))
        JoystickButton(bongos, 2).whileHeld(RunStorageConstant(storage, -0.8))
        JoystickButton(bongos, 4).whileHeld(RunIntakeConstant(storage, intake, pivot, 1.0))
        JoystickButton(bongos, 3).whileHeld(RunIntakeConstant(storage, intake, pivot, -1.0))
        JoystickButton(bongos, 10).whenPressed(RunShooter(shooter, {cameraTable.getEntry("targetArea").getDouble(3800.0)}, false).withTimeout(   10.0))

    }

    fun rumbleController(rumbleLevel: Double) {
        secondaryController.setRumble(GenericHID.RumbleType.kRightRumble, rumbleLevel)
    }
    fun getAutoCommand(): Command {
        return FinalAutoGroup(pivot, driveTrain, shooter, storage, cameraTable)
    }
}