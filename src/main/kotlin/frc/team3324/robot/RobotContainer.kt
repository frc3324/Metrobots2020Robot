package frc.team3324.robot

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.XboxController.Button
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.team3324.robot.drivetrain.DriveTrain
import frc.team3324.robot.drivetrain.commands.teleop.Drive
import frc.team3324.robot.drivetrain.commands.teleop.ToggleAutoShifting
import frc.team3324.robot.intake.Intake
import frc.team3324.robot.intake.commands.RunIntake
import frc.team3324.robot.shooter.Shooter
import frc.team3324.robot.shooter.commands.RunShooter
import frc.team3324.robot.util.Camera
import frc.team3324.robot.util.PneumaticShift

class RobotContainer {
    private val intake = Intake()
    private val driveTrain = DriveTrain()
    private val shooter = Shooter()
    private val primaryController = XboxController(0)
    private val secondaryController = XboxController(1)

    private val primaryRightX: Double
        get() = primaryController.getX(GenericHID.Hand.kLeft)
    private val primaryLeftY: Double
        get() = primaryController.getY(GenericHID.Hand.kRight)


   init {
       Camera.schedule()
       driveTrain.defaultCommand = Drive(driveTrain, {primaryController.getY(GenericHID.Hand.kLeft)}, {primaryController.getX(GenericHID.Hand.kRight)})
       configureButtonBindings()

   }

    fun configureButtonBindings() {
        JoystickButton(primaryController, Button.kBumperLeft.value).whenPressed(PneumaticShift(driveTrain.gearShifter))
        JoystickButton(primaryController, Button.kA.value).whenPressed(ToggleAutoShifting(driveTrain))
        JoystickButton(primaryController, Button.kB.value).whenPressed(RunIntake(intake))
        JoystickButton(secondaryController, Button.kA.value).whenPressed(RunShooter(shooter, 5000.0))
    }
}