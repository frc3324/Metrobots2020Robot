package frc.team3324.robot.drivetrain.commands.teleop

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj.GenericHID.Hand
import frc.team3324.robot.drivetrain.DriveTrain

class Drive(val driveTrain: DriveTrain, val xSpeed: (GenericHID.Hand) -> Double, val ySpeed: (GenericHID.Hand) -> Double): CommandBase() {

    init {
        addRequirements(driveTrain)
    }

    override fun execute() {
        driveTrain.curvatureDrive(xSpeed(Hand.kLeft), ySpeed(Hand.kRight))
    }

    override fun isFinished(): Boolean {
        return false
    }
}