package frc.team3324.robot

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.*
import edu.wpi.first.wpilibj.XboxController.Button
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.PIDCommand
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.team3324.library.commands.MotorCommand
import frc.team3324.robot.autocommands.FinalAutoGroup
import frc.team3324.robot.drivetrain.DriveTrain
import frc.team3324.robot.drivetrain.commands.teleop.Drive
import frc.team3324.robot.drivetrain.commands.teleop.GyroTurn
import frc.team3324.robot.intake.Intake
import frc.team3324.robot.intake.Pivot
import frc.team3324.robot.shooter.Shooter
import frc.team3324.robot.shooter.commands.RunShooter
import frc.team3324.robot.shooter.commands.StopShooter
import frc.team3324.robot.util.Camera
import frc.team3324.robot.util.Consts
import frc.team3324.library.commands.ToggleLightCommand
import frc.team3324.library.subsystems.MotorSubsystem
import io.github.oblarg.oblog.Logger

class RobotContainer {
    private val intake = Intake()
    private val storage = MotorSubsystem(listOf(Consts.Storage.TOP_MOTOR, Consts.Storage.BOTTOM_MOTOR))
    private val driveTrain = DriveTrain()
    private val climber = MotorSubsystem(listOf(Consts.Climber.LEFT_MOTOR, Consts.Climber.RIGHT_MOTOR))
    private val pivot = Pivot()
    private val shooter = Shooter(Consts.Shooter.LEFT_MOTOR, Consts.Shooter.RIGHT_MOTOR)


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


       pivot.defaultCommand = MotorCommand(pivot, -0.05)
       intake.defaultCommand = MotorCommand(intake, if (secondTriggerRight > 0.0) secondTriggerRight * 0.5 else -secondTriggerLeft * 0.5)
       configureButtonBindings()

   }

    private fun configureButtonBindings() {
        JoystickButton(primaryController, Button.kBumperLeft.value).whileHeld(MotorCommand(pivot, -0.5))
        JoystickButton(primaryController, Button.kBumperRight.value).whileHeld(MotorCommand(pivot, 0.5))
        JoystickButton(primaryController, Button.kX.value).whenPressed(ToggleLightCommand(Robot.light))
        JoystickButton(primaryController, Button.kA.value).whileHeld(MotorCommand(climber, -1.0, 0)) // run the left climber motor
        JoystickButton(primaryController, Button.kB.value).whileHeld(MotorCommand(climber, -1.0, 1)) // run the right climber motor
        JoystickButton(primaryController, Button.kStickLeft.value).whileHeld(MotorCommand(climber, 1.0, 0))
        JoystickButton(primaryController, Button.kStickRight.value).whileHeld(MotorCommand(climber,  1.0, 1))
        JoystickButton(primaryController, Button.kY.value).whileHeld(GyroTurn(
                driveTrain,
                1.0/70.0,
                (Consts.DriveTrain.ksVolts + 0.3)/12,
                {cameraTable.getEntry("targetYaw").getDouble(0.0)},
                {input -> driveTrain.curvatureDrive(0.0, input, true)}
        ))
        JoystickButton(secondaryController, Button.kX.value).whenPressed(RunShooter(shooter, {cameraTable.getEntry("targetArea").getDouble(3800.0)}, false).withTimeout(10.0))
        JoystickButton(secondaryController, Button.kY.value).whenPressed(RunShooter(shooter, {cameraTable.getEntry("targetArea").getDouble(3800.0)}, true).withTimeout(10.0))
        JoystickButton(secondaryController, Button.kBumperLeft.value).whileHeld(MotorCommand(storage, 0.799999998, 0).alongWith(MotorCommand(storage, 0.6, 1)))
        JoystickButton(secondaryController, Button.kBumperRight.value).whileHeld(MotorCommand(storage, -0.799999998, 0).alongWith(MotorCommand(storage, -0.6, 1)))

        JoystickButton(secondaryController, Button.kA.value).whileHeld(MotorCommand(intake, -1.0))
        JoystickButton(secondaryController, Button.kB.value).whileHeld(MotorCommand(intake, 1.0))
        JoystickButton(secondaryController, Button.kStart.value).whenPressed(StopShooter(shooter))
        JoystickButton(bongos, 1).whileHeld(MotorCommand(storage, 1.066666664, 0).alongWith(MotorCommand(storage, 0.8, 1)))
        JoystickButton(bongos, 2).whileHeld(MotorCommand(storage, -1.066666664, 0).alongWith(MotorCommand(storage, -0.8, 1)))
        JoystickButton(bongos, 4).whileHeld(MotorCommand(intake, 1.0))
        JoystickButton(bongos, 3).whileHeld(MotorCommand(intake, -1.0))
        JoystickButton(bongos, 10).whenPressed(RunShooter(shooter, {cameraTable.getEntry("targetArea").getDouble(3800.0)}, false).withTimeout(   10.0))

    }

    fun rumbleController(rumbleLevel: Double) {
        secondaryController.setRumble(GenericHID.RumbleType.kRightRumble, rumbleLevel)
    }
    fun getAutoCommand(): Command {
        return FinalAutoGroup(pivot, driveTrain, shooter, storage, cameraTable)
    }
}