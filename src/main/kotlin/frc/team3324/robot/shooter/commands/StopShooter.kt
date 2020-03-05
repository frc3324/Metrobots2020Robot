package frc.team3324.robot.shooter.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.team3324.robot.shooter.Shooter

class StopShooter(val shooter: Shooter): InstantCommand() {

    init {
        addRequirements(shooter)
    }

    override fun execute() {
        shooter.percentPower = 0.0
    }
}