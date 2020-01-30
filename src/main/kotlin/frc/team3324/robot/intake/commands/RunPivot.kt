package frc.team3324.robot.intake.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.intake.Intake

class RunPivot(val intake: Intake, val speed: Double): CommandBase() {
    init {
        addRequirements(intake)
    }

    override fun execute() {
        intake.pivot = speed
    }

    override fun end(interrupted: Boolean) {
        intake.pivot = 0.0
    }
}