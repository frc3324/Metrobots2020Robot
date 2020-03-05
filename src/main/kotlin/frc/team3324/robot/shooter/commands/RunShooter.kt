package frc.team3324.robot.shooter.commands

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.Robot
import frc.team3324.robot.shooter.Shooter

class RunShooter(val shooter: Shooter, val area: () -> Double, val trench: Boolean): CommandBase() {

    init {
        Robot.light.set(true)
        addRequirements(shooter)
    }

    override fun execute() {
        var rpm = 1116 * Math.pow(area(), -0.365)
        if (trench) {
            rpm = 1116 * Math.pow(area(), -0.338)
        }
       shooter.RPM = rpm
        Robot.robotContainer.rumbleController(1.0)
    }

    override fun end(interrupted: Boolean) {
        shooter.RPM = 0.0
    }
}