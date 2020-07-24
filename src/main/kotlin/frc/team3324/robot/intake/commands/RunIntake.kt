package frc.team3324.robot.intake.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.intake.Intake

class RunIntake(val intake: Intake, val speedOne: () -> Double, val speedTwo: () -> Double): CommandBase() {

    init {
        addRequirements(intake)
    }

    override fun execute() {
        if (speedOne() > 0.0) {
            intake.setSpeed(speedOne() * 0.5)
        } else if (speedTwo() > 0.0) {
            intake.setSpeed(-speedTwo() * 0.5)
        } else {
            intake.setSpeed(0.0)
        }
    }

    override fun end(interrupted: Boolean) {
        intake.setSpeed(0.0)
    }

}
