package frc.team3324.robot.storage.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.storage.Storage

class RunStorageConstant(val storage: Storage, val speed: Double, val selector: Int): CommandBase() {
    init {
        addRequirements(storage)
    }

    override fun execute() {
        when(selector) {
            0 -> {
                storage.botSpeed = speed;
                storage.topSpeed = speed;
            }
            1 -> storage.botSpeed = speed;
            2 -> storage.topSpeed = speed;
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