package frc.team3324.robot.intake.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.intake.Intake
import frc.team3324.robot.storage.Storage

class RunIntake(val intake: Intake, val speed: Double): CommandBase() {

    init {
        addRequirements(intake)
    }

    override fun execute() {
        intake.speed = speed
    }

    override fun end(interrupted: Boolean) {
        intake.speed = 0.0
    }

}
