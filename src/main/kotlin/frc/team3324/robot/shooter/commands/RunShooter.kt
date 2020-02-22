package frc.team3324.robot.shooter.commands

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.shooter.Shooter

class RunShooter(val shooter: Shooter, val area: () -> Double): CommandBase() {

    init {
        addRequirements(shooter)
    }

    override fun execute() {
        val area = area()
        val rpm = 1113 * Math.pow(area(), -0.33)
       shooter.RPM = rpm
    }

    override fun end(interrupted: Boolean) {
        shooter.RPM = 0.0
    }
}