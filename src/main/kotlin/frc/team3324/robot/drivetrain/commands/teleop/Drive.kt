package frc.team3324.robot.drivetrain.commands.teleop

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj.GenericHID.Hand
import frc.team3324.robot.drivetrain.DriveTrain

class Drive(val driveTrain: DriveTrain, val xSpeed: () -> Double, val ySpeed: () -> Double): CommandBase() {

    init {
        addRequirements(driveTrain)
    }

    override fun execute() {
        driveTrain.curvatureDrive(-xSpeed(), ySpeed())
    }

    override fun isFinished(): Boolean {
        return false
    }
}