package frc.team3324.robot.drivetrain.commands.teleop

import edu.wpi.first.wpilibj.command.Command
import frc.team3324.robot.drivetrain.DriveTrain
import frc.team3324.robot.util.OI

object Drive: Command() {

    init {
        requires(DriveTrain)
    }

    override fun execute() {
        val xSpeed = OI.primaryLeftY
        val ySpeed = OI.primaryRightX

        DriveTrain.curvatureDrive(xSpeed, ySpeed)
    }

    override fun isFinished(): Boolean {
        return false
    }
}