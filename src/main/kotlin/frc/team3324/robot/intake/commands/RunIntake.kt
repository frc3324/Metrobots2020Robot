package frc.team3324.robot.intake.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.intake.Intake
import frc.team3324.robot.storage.Storage

class RunIntake(val storage: Storage, val intake: Intake, val speed: Double): CommandBase() {

    init {
        addRequirements(intake, storage)
    }

    override fun execute() {
        intake.speed = speed
        storage.speed = speed
    }

    override fun end(interrupted: Boolean) {
        intake.speed = 0.0
        storage.speed = 0.0
    }

}
