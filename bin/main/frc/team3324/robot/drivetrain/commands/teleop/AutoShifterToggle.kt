package frc.team3324.robot.drivetrain.commands.teleop

import edu.wpi.first.wpilibj.command.InstantCommand
import frc.team3324.robot.util.DataCollector

object disableAutoShifter: InstantCommand() {

    override fun initialize() {
        DataCollector.enabled != DataCollector.enabled
    }
}