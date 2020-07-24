package frc.team3324.library.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.library.subsystems.MotorSubsystem

class MotorCommand(val subsystem: MotorSubsystem, val speed: Double, val index: Int? = null): CommandBase() {
    init {
        addRequirements(subsystem)
    }

    override fun execute() {
        if (index != null) {
            subsystem.setSpeed(speed, index)
        } else {
            subsystem.setSpeed(speed)
        }
    }

    override fun isFinished(): Boolean {
        return false
    }
}
