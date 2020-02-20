package frc.team3324.robot

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.Joystick
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
    private val ddrPad = Joystick(2)

    private val DDR_START_BUTTON = JoystickButton(ddrPad, 10)
    private val DDR_SELECT_BUTTON = JoystickButton(ddrPad, 8)
    private val DDR_A_BUTTON = JoystickButton(ddrPad, 2)
    private val DDR_B_BUTTON = JoystickButton(ddrPad, 3)
    private val DDR_UP_BUTTON = JoystickButton(ddrPad, 13)
    private val DDR_DOWN_BUTTON = JoystickButton(ddrPad, 15)
    private val DDR_LEFT_BUTTON = JoystickButton(ddrPad, 16)
    private val DDR_RIGHT_BUTTON = JoystickButton(ddrPad, 14)

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
       driveTrain.defaultCommand = Drive(driveTrain, this::ddrDriveForward, this::ddrDriveTurn)


       pivot.defaultCommand = RunPivot(pivot, -0.05)
       //intake.defaultCommand = RunIntake(storage, intake, this::primaryTriggerLeft, this::primaryTriggerRight)
       storage.defaultCommand = RunStorage(storage, this::secondTriggerLeft, this::secondTriggerRight, this::secondRightY, this::secondLeftY)
       configureButtonBindings()

   }

    fun ddrDriveForward(): Double {
        when {
            DDR_UP_BUTTON.get() -> return -0.3
            DDR_DOWN_BUTTON.get() -> return 0.3
            else -> return 0.0
        }
    }

    fun ddrDriveTurn(): Double {
        when {
            DDR_LEFT_BUTTON.get() -> return -0.3
            DDR_RIGHT_BUTTON.get() -> return 0.3
            else -> return 0.0
        }
    }

    fun configureButtonBindings() {
//        JoystickButton(primaryController, Button.kBumperLeft.value).whileHeld(RunPivot(pivot, 0.5))
//        JoystickButton(primaryController, Button.kBumperRight.value).whileHeld(RunPivot(pivot, -0.5))
//        JoystickButton(primaryController, Button.kX.value).whenPressed(SwitchRelay(relay))
//        JoystickButton(primaryController, Button.kA.value).whileHeld(RunClimber(climber, -1.0, {input: Double -> climber.leftSpeed = input}))
//        JoystickButton(primaryController, Button.kB.value).whileHeld(RunClimber(climber, -1.0, {input: Double -> climber.rightSpeed = input}))
        DDR_A_BUTTON.whenPressed(GyroTurn(
                driveTrain,
                1.0/90.0,
                Consts.DriveTrain.ksVolts/12,
                {cameraTable.getEntry("targetYaw").getDouble(0.0)},
                {input -> driveTrain.curvatureDrive(0.0, input, true)}
        ))
        /*DDR_A_BUTTON.whenPressed(RunShooter(shooter, {cameraTable.getEntry("targetArea").getDouble(3800.0)}).withTimeout(5.0))

        DDR_START_BUTTON.whileHeld(RunClimber(climber, 1.0, {input: Double -> climber.leftSpeed = input; climber.rightSpeed = input}))
        DDR_SELECT_BUTTON.whileHeld(RunClimber(climber, -1.0, {input: Double -> climber.leftSpeed = input; climber.rightSpeed = input}))
        DDR_RIGHT_BUTTON.whileHeld(RunStorageConstant(storage, -0.6))
        DDR_LEFT_BUTTON.whileHeld(RunStorageConstant(storage, 0.6))
*/
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