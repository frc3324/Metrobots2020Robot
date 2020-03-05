package frc.team3324.robot.drivetrain.commands.teleop

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj.GenericHID.Hand
import frc.team3324.robot.drivetrain.DriveTrain
import kotlin.math.sign

class Drive(val driveTrain: DriveTrain, val xSpeed: () -> Double, val ySpeed: () -> Double): CommandBase() {

    init {
        addRequirements(driveTrain)
    }

    override fun execute() {
        driveTrain.curvatureDrive(xSpeed(), ySpeed() + (sign(ySpeed()) * 0.01))
    }

    override fun isFinished(): Boolean {
        return false
    }
}