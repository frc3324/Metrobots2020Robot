package frc.team3324.robot.autocommands

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.shooter.Shooter
import frc.team3324.robot.storage.Storage

class ShootOneBall(val area: Double, val shooter: Shooter): CommandBase() {

    init {
        addRequirements(shooter)
    }

    override fun execute() {
        val rpm = 7400 - (171000 * area) + 2.5e+6 * Math.pow(area, 2.0)
        shooter.RPM = rpm
    }

    override fun isFinished(): Boolean {
        return (shooter.leftMotorCurrent > 20.0)
    }
}