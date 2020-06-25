package frc.team3324.library.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.library.subsystems.MotorSubsystem

class MotorCommand(val subsystem: MotorSubsystem, val motorName: String, val speed: Double): CommandBase() {
    override fun execute() {
        subsystem.setSpeed(motorName, speed)
    }

    override fun isFinished(): Boolean {
        return false
    }
}
