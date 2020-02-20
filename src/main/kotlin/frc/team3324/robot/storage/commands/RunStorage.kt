package frc.team3324.robot.storage.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.storage.Storage

class RunStorage(val storage: Storage, val ySpeedBot: () -> Double, val ySpeedTop: () -> Double, val joystickBot: () -> Double, val joystickTop: () -> Double): CommandBase() {
    init {
        this.addRequirements(storage)
    }

    override fun execute() {
        if (ySpeedBot() > 0.0) {
            storage.botSpeed = ySpeedBot()
            storage.topSpeed = ySpeedBot()
        } else if (Math.abs(joystickBot()) > 0.0) {
            storage.botSpeed = joystickBot()
        } else if (Math.abs(joystickTop()) > 0.0) {
            storage.topSpeed = joystickTop()
        } else {
            storage.botSpeed = -ySpeedTop()
            storage.topSpeed = -ySpeedTop()
        }
    }

    override fun end(interrupted: Boolean) {
        storage.botSpeed = 0.0
    }
}