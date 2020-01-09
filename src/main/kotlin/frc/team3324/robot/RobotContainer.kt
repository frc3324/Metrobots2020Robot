package frc.team3324.robot

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.XboxController.Button
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.team3324.robot.drivetrain.DriveTrain
import frc.team3324.robot.drivetrain.commands.teleop.Drive
import frc.team3324.robot.drivetrain.commands.teleop.ShiftGears
import frc.team3324.robot.drivetrain.commands.teleop.ToggleAutoShifting
import frc.team3324.robot.intake.Intake
import frc.team3324.robot.intake.Run
import frc.team3324.robot.util.AutoShifter
import frc.team3324.robot.util.Camera

class RobotContainer {
    private val intake = Intake()
    private val driveTrain = DriveTrain()
    private val primaryController = XboxController(0)
    private val autoShifter = AutoShifter(driveTrain)




   init {
       Camera.schedule()
       driveTrain.defaultCommand = Drive(driveTrain, primaryController::getX, primaryController::getY)
       configureButtonBindings()

   }

    fun configureButtonBindings() {
        JoystickButton(primaryController, Button.kBumperLeft.value).whenPressed(ShiftGears(driveTrain))
        JoystickButton(primaryController, Button.kA.value).whenPressed(ToggleAutoShifting(autoShifter))
        JoystickButton(primaryController, Button.kB.value).whenPressed(Run(intake))
    }
}