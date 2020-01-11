package frc.team3324.robot.intake

import edu.wpi.first.wpilibj2.command.CommandBase

class Run(val intake: Intake): CommandBase() {

    init {
        addRequirements(intake)
    }

    override fun execute() {
        intake.run(0.5)
    }

}
