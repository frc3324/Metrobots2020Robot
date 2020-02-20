package frc.team3324.robot.storage.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.storage.Storage

class RunStorageConstant(val storage: Storage, val speed: Double): CommandBase() {
    init {
        addRequirements(storage)
    }

    override fun execute() {
        storage.botSpeed = speed
        storage.topSpeed = speed
    }

    override fun end(interrupted: Boolean) {
        storage.botSpeed = 0.0
        storage.topSpeed = 0.0
    }

    override fun isFinished(): Boolean {
        return false
    }
}