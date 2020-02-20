package frc.team3324.robot.climber.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.climber.Climber

class RunClimber(val climber : Climber, val speed: Double, val output: (Double) -> Unit): CommandBase() {

    override fun execute() {
        output(speed)
    }

    override fun end(interrupted: Boolean) {
        output(0.0)
    }
}