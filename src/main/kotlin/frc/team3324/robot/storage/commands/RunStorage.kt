package frc.team3324.robot.storage.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.storage.Storage

class RunStorage(val storage: Storage, val ySpeedBot: () -> Double, val ySpeedTop: () -> Double, val joystickBot: () -> Double, val joystickTop: () -> Double): CommandBase() {
    init {
        this.addRequirements(storage)
    }

    override fun execute() {
        if (ySpeedBot() > 0.0) {
            storage.motorBottom.set(ySpeedBot())
            storage.motorTop.set(ySpeedBot())
        } else if (joystickBot() > 0.0) {
            storage.motorBottom.set(joystickBot())
        } else if (joystickTop() > 0.0) {
            storage.motorTop.set(joystickTop())
        } else {
            storage.motorBottom.set(-ySpeedTop())
            storage.motorTop.set(-ySpeedTop())
        }
    }

    override fun end(interrupted: Boolean) {
        storage.speed = 0.0
    }
}