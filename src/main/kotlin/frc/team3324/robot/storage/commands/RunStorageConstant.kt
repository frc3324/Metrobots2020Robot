package frc.team3324.robot.storage.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.storage.Storage

class RunStorageConstant(val storage: Storage, val speed: Double, val selector: STORAGE_TYPE): CommandBase() {
    enum class STORAGE_TYPE {
        BOTH, BOT, TOP
    }
    init {
        addRequirements(storage)
    }

    override fun execute() {
        if (selector == STORAGE_TYPE.BOTH) {
            storage.botSpeed = speed
            storage.topSpeed = speed
        } else if (selector == STORAGE_TYPE.BOT) {
            storage.botSpeed = speed
        } else if (selector == STORAGE_TYPE.TOP) {
            storage.topSpeed = speed
        }
    }

    override fun end(interrupted: Boolean) {
        storage.botSpeed = 0.0
        storage.topSpeed = 0.0
    }

    override fun isFinished(): Boolean {
        return false
    }
}