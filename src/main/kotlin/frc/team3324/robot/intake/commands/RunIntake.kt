package frc.team3324.robot.intake.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.intake.Intake

class RunIntake(val intake: Intake): CommandBase() {

    init {
        addRequirements(intake)
    }

    override fun execute() {
        intake.run(0.5)
    }

}
