package frc.team3324.robot.intake.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.intake.Intake

class RunIntake(val intake: Intake, val speed: Double): CommandBase() {

    init {
    }

    override fun execute() {
        intake.run(speed)
    }

    override fun end(interrupted: Boolean) {
        intake.run(0.0)
    }

}
