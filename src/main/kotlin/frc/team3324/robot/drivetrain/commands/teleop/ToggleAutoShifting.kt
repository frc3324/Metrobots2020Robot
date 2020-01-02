package frc.team3324.robot.drivetrain.commands.teleop

import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.team3324.robot.util.AutoShifter

class ToggleAutoShifting(val autoShifter: AutoShifter): InstantCommand() {

    override fun initialize() {
        autoShifter.enabled = !autoShifter.enabled
    }
}