package frc.team3324.robot

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.Relay
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.XboxController.Button
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.team3324.robot.autocommands.ShooterAndStorageFiveBalls
import frc.team3324.robot.climber.Climber
import frc.team3324.robot.climber.commands.RunClimber
import frc.team3324.robot.drivetrain.DriveTrain
import frc.team3324.robot.drivetrain.commands.teleop.Drive
import frc.team3324.robot.drivetrain.commands.teleop.GyroTurn
import frc.team3324.robot.intake.Intake
import frc.team3324.robot.intake.Pivot
import frc.team3324.robot.intake.commands.RunIntake
import frc.team3324.robot.intake.commands.RunPivot
import frc.team3324.robot.shooter.Shooter
import frc.team3324.robot.shooter.commands.RunShooter
import frc.team3324.robot.storage.Storage
import frc.team3324.robot.storage.commands.RunStorage
import frc.team3324.robot.storage.commands.RunStorageConstant
import frc.team3324.robot.util.Camera
import frc.team3324.robot.util.Consts
import frc.team3324.robot.util.SwitchRelay
import io.github.oblarg.oblog.Logger

class RobotContainer {
    private val intake = Intake()
    private val storage = Storage()
    private val driveTrain = DriveTrain()
    private val climber = Climber()
    private val pivot = Pivot()
    private val relay = Relay(0)
    private val shooter = Shooter()

    private val table = NetworkTableInstance.getDefault()
    private val cameraTable = table.getTable("chameleon-vision").getSubTable("usb")

    private val primaryController = XboxController(0)
    private val secondaryController = XboxController(1)

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
       Logger.configureLoggingAndConfig(this, true)
       Camera.schedule()
       driveTrain.defaultCommand = Drive(driveTrain, {primaryController.getY(GenericHID.Hand.kLeft)}, {primaryController.getX(GenericHID.Hand.kRight)})


       pivot.defaultCommand = RunPivot(pivot, -0.05)
       intake.defaultCommand = RunIntake(storage, intake, this::primaryTriggerLeft, this::primaryTriggerRight)
       storage.defaultCommand = RunStorage(storage, this::secondTriggerLeft, this::secondTriggerRight, this::secondRightY, this::secondLeftY)
       configureButtonBindings()

   }

    fun configureButtonBindings() {
        JoystickButton(primaryController, Button.kBumperLeft.value).whileHeld(RunPivot(pivot, 0.5))
        JoystickButton(primaryController, Button.kBumperRight.value).whileHeld(RunPivot(pivot, -0.5))
        JoystickButton(primaryController, Button.kX.value).whenPressed(SwitchRelay(relay))
        JoystickButton(primaryController, Button.kA.value).whileHeld(RunClimber(climber, -1.0, {input: Double -> climber.leftSpeed = input}))
        JoystickButton(primaryController, Button.kB.value).whileHeld(RunClimber(climber, -1.0, {input: Double -> climber.rightSpeed = input}))
        JoystickButton(primaryController, Button.kY.value).whenPressed(GyroTurn(
                driveTrain,
                1.0/90.0,
                Consts.DriveTrain.ksVolts/12,
                {cameraTable.getEntry("targetYaw").getDouble(0.0)},
                {input -> driveTrain.curvatureDrive(0.0, input, true)}
        ))
        JoystickButton(secondaryController, Button.kX.value).whenPressed(RunShooter(shooter, {cameraTable.getEntry("targetArea").getDouble(3800.0)}).withTimeout(10.0))
        JoystickButton(secondaryController, Button.kA.value).whileHeld(RunClimber(climber, 1.0, {input: Double -> climber.leftSpeed = input; climber.rightSpeed = input}))
        JoystickButton(secondaryController, Button.kB.value).whileHeld(RunClimber(climber, -1.0, {input: Double -> climber.leftSpeed = input; climber.rightSpeed = input}))
        JoystickButton(secondaryController, Button.kBumperLeft.value).whileHeld(RunStorageConstant(storage, -0.6))
        JoystickButton(secondaryController, Button.kBumperRight.value).whileHeld(RunStorageConstant(storage, 0.6))

    }

    fun getAutoCommand(): Command {
        return RunShooter(shooter, {cameraTable.getEntry("targetArea").getDouble(3800.0)}).withTimeout(1.0).andThen(GyroTurn(
                driveTrain,
                1.0/90.0,
                Consts.DriveTrain.ksVolts/12,
                {cameraTable.getEntry("targetYaw").getDouble(0.0)},
                {input -> driveTrain.curvatureDrive(0.0, input, true)}
        ).andThen(ShooterAndStorageFiveBalls(shooter, {cameraTable.getEntry("targetArea").getDouble(0.0)}, storage, 3.0)))
    }
}