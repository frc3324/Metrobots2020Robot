package frc.team3324.robot.util

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.buttons.JoystickButton
import frc.team3324.robot.arm.Arm
import frc.team3324.robot.arm.commands.ResetArm
import frc.team3324.robot.arm.commands.StopArm
import frc.team3324.robot.climber.Climber
import frc.team3324.robot.drivetrain.DriveTrain
import frc.team3324.robot.drivetrain.commands.teleop.FollowPath
import frc.team3324.robot.drivetrain.commands.teleop.ShiftGears
import frc.team3324.robot.intake.cargo.commands.Intake
import frc.team3324.robot.intake.cargo.commands.Outtake
import frc.team3324.robot.intake.hatch.Hatch
import frc.team3324.robot.intake.hatch.commands.SwitchIntake

object OI {
    val oneEightyDegree = PIDCommand(0.55, 0.001, 0.0, Math.toRadians(180.0), 0.01, Arm, Arm::getArmRadians, Arm::setSpeed)
    val zeroDegree = PIDCommand(0.55, 0.001, 0.0, Math.toRadians(0.0), 0.01, Arm, Arm::getArmRadians, Arm::setSpeed)

    private const val BUTTON_A = 1
    private const val BUTTON_B = 2
    private const val BUTTON_X = 3
    private const val BUTTON_Y = 4
    private const val LEFT_BUMPER = 5
    private const val RIGHT_BUMPER = 6
    private const val BUTTON_BACK = 7
    private const val BUTTON_START = 8
    private const val JOYSTICK_LEFT_CLICK = 9
    private const val JOYSTICK_RIGHT_CLICK = 10

    private val primaryController = XboxController(0)
    private val secondaryController = XboxController(1)

    private val PRIMARY_A_BUTTON = JoystickButton(primaryController, BUTTON_A)
    private val PRIMARY_X_BUTTON = JoystickButton(primaryController, BUTTON_X)
    private val PRIMARY_Y_BUTTON = JoystickButton(primaryController, BUTTON_Y)
    private val PRIMARY_B_BUTTON = JoystickButton(primaryController, BUTTON_B)
    private val PRIMARY_START_BUTTON = JoystickButton(primaryController, BUTTON_START)
    private val PRIMARY_BACK_BUTTON = JoystickButton(primaryController, BUTTON_BACK)
    private val PRIMARY_RIGHT_BUMPER = JoystickButton(primaryController, RIGHT_BUMPER)
    private val PRIMARY_LEFT_BUMPER = JoystickButton(primaryController, LEFT_BUMPER)

    private val SECONDARY_A_BUTTON = JoystickButton(secondaryController, BUTTON_A)
    private val SECONDARY_B_BUTTON = JoystickButton(secondaryController, BUTTON_B)
    private val SECONDARY_X_BUTTON = JoystickButton(secondaryController, BUTTON_X)
    private val SECONDARY_Y_BUTTON = JoystickButton(secondaryController, BUTTON_Y)
    private val SECONDARY_BACK_BUTTON = JoystickButton(secondaryController, BUTTON_BACK)
    private val SECONDARY_START_BUTTON = JoystickButton(secondaryController, BUTTON_START)
    private val SECONDARY_LEFT_JOYSTICK_BUTTON = JoystickButton(secondaryController, JOYSTICK_LEFT_CLICK)
    private val SECONDARY_RIGHT_JOYSTICK_BUTTON = JoystickButton(secondaryController, JOYSTICK_RIGHT_CLICK)
    private val SECONDARY_RIGHT_BUMPER = JoystickButton(secondaryController, RIGHT_BUMPER)
    private val SECONDARY_LEFT_BUMPER = JoystickButton(secondaryController, LEFT_BUMPER)


    val secondaryLeftY get() = secondaryController.getY(GenericHID.Hand.kLeft)
    val secondaryRightY get() = secondaryController.getY(GenericHID.Hand.kRight)

    val primaryLeftY get() = primaryController.getY(GenericHID.Hand.kLeft)
    val primaryRightX get() = primaryController.getX(GenericHID.Hand.kRight)

    init {
        PRIMARY_RIGHT_BUMPER.whenPressed(PneumaticShift(Hatch.hatchIntake))
        PRIMARY_LEFT_BUMPER.whenPressed(PneumaticShift(DriveTrain.gearShifter))

        PRIMARY_BACK_BUTTON.whenPressed(PneumaticShift(Climber.frontClimber))
        PRIMARY_START_BUTTON.whenPressed(PneumaticShift(Climber.backClimber))
        PRIMARY_A_BUTTON.whenPressed(FollowPath(TrapezoidProfile.State(0.2, 0.0), 5.0, (1/Consts.DriveTrain.HIGH_GEAR_MAX_VELOCITY)))

        SECONDARY_RIGHT_BUMPER.whenPressed(oneEightyDegree)
        SECONDARY_LEFT_BUMPER.whenPressed(zeroDegree)
        SECONDARY_A_BUTTON.whenPressed(PIDCommand(0.65, 0.001, 0.0, Math.toRadians(90.0), 0.01, Arm, Arm::getArmRadians, Arm::setSpeed))
        SECONDARY_B_BUTTON.whenPressed(StopArm())
        SECONDARY_START_BUTTON.whenPressed(ResetArm())

        SECONDARY_X_BUTTON.whileHeld(Intake())
        SECONDARY_Y_BUTTON.whileHeld(Outtake())
    }


}