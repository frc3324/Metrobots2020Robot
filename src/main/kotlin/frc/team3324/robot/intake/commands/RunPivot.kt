package frc.team3324.robot.intake.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.intake.Pivot

class RunPivot(val pivot: Pivot, val speed: Double): CommandBase() {
    init {
        addRequirements(pivot)
    }

    override fun execute() {
        pivot.setSpeed(speed)
    }

    override fun end(interrupted: Boolean) {
        pivot.setSpeed(0.0)
    }
}