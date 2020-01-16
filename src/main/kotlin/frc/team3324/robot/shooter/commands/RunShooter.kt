package frc.team3324.robot.shooter.commands

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.shooter.Shooter

class RunShooter(val shooter: Shooter, val rpm: Double): CommandBase() {

    init {
        addRequirements(shooter)
    }

    override fun execute() {
       shooter.RPM = rpm
        SmartDashboard.putNumber("RPM", shooter.RPM)
    }

    override fun end(interrupted: Boolean) {
        shooter.RPM = 0.0
    }
}