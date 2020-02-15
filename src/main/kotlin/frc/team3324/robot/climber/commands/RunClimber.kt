package frc.team3324.robot.climber.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.climber.Climber

class RunClimber(val climber : Climber, val speed: Double): CommandBase() {
    init {
        addRequirements(climber)
    }

    override fun execute() {
        climber.speedForward = speed
    }

    override fun end(interrupted: Boolean) {
        climber.speedForward = 0.0
    }
}