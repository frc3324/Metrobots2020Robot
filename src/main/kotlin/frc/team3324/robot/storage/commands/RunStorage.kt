package frc.team3324.robot.storage.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.storage.Storage

class RunStorage(val storage: Storage): CommandBase() {
    init {
        this.addRequirements(storage)
    }

    override fun execute() {
        storage.speed = 0.5
    }

    override fun end(interrupted: Boolean) {
        storage.speed = 0.0
    }
}