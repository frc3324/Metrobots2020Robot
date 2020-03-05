package frc.team3324.robot.intake.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.intake.Intake
import frc.team3324.robot.intake.Pivot
import frc.team3324.robot.storage.Storage
import kotlin.math.sign

class RunIntakeConstant(val storage: Storage, val intake: Intake, val pivot: Pivot, val speed: Double): CommandBase() {

    init {
        addRequirements(intake)
    }

    override fun execute() {
        pivot.pivot = 0.07
        intake.speed = speed
//        storage.botSpeed = 0.6 * -sign(speed)
    }

    override fun end(interrupted: Boolean) {
        storage.botSpeed = 0.0
        intake.speed = 0.0
    }
}