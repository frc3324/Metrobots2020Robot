package frc.team3324.robot.intake.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.intake.Intake
import frc.team3324.robot.storage.Storage

class RunIntake(val storage: Storage, val intake: Intake, val speedOne: () -> Double, val speedTwo: () -> Double): CommandBase() {

    init {
        addRequirements(intake)
    }

    override fun execute() {
        if (speedOne() > 0.0) {
            intake.speed = speedOne() * 0.5
        } else if (speedTwo() > 0.0) {
            intake.speed = -speedTwo() * 0.5
        } else {
            intake.speed = 0.0
        }
    }

    override fun end(interrupted: Boolean) {
        intake.speed = 0.0
    }

}
