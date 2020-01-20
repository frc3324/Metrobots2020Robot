package frc.team3324.robot

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.XboxController.Button
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.team3324.robot.intake.Intake
import frc.team3324.robot.intake.commands.RunIntake
import frc.team3324.robot.shooter.Shooter
import frc.team3324.robot.shooter.commands.RunShooter
import frc.team3324.robot.util.Camera

class RobotContainer {
    private val intake = Intake()
    private val shooter = Shooter()
    private val shooterCommand = RunShooter(shooter, 1000.0)
    private val primaryController = XboxController(0)
    private val secondaryController = XboxController(1)

    private val primaryRightX: Double
        get() = primaryController.getX(GenericHID.Hand.kLeft)
    private val primaryLeftY: Double
        get() = primaryController.getY(GenericHID.Hand.kRight)


   init {
       Camera.schedule()
       configureButtonBindings()
   }

    fun configureButtonBindings() {
        JoystickButton(primaryController, Button.kB.value).whenPressed(RunIntake(intake))
        JoystickButton(secondaryController, Button.kA.value).whenPressed(RunShooter(shooter, 3990.0))
    }
}