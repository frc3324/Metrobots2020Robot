package frc.team3324.robot.drivetrain.commands.teleop

import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.team3324.robot.drivetrain.DriveTrain

class ToggleAutoShifting(val driveTrain: DriveTrain): InstantCommand() {

    override fun execute() {
        driveTrain.enabled = !driveTrain.enabled

    }
}