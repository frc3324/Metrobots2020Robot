package frc.team3324.robot.intake.cargo.commands

import edu.wpi.first.wpilibj.command.Command
import frc.team3324.robot.intake.cargo.Cargo

class Feedforward: Command() {
    init {
        requires(Cargo)
    }

    override fun execute() {
        Cargo.speed = 0.06
    }

    override fun isFinished(): Boolean {
        return false
    }
}
