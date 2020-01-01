package frc.team3324.robot.drivetrain.commands.teleop

import edu.wpi.first.wpilibj.command.Command
import frc.team3324.robot.drivetrain.DriveTrain
import frc.team3324.robot.util.Consts

object DataCollector: Command() {

    override fun execute() {
        DriveTrain.leftEncoderSpeed
        Consts.DriveTrain.CIRCUMFERENCE
        DriveTrain.rightEncoderSpeed

    }

    fun getRPM(circum: Double, speed: Double): Double {
        return (speed/circum) * 60;
    }

    val lRPM = getRPM(Consts.DriveTrain.CIRCUMFERENCE, DriveTrain.leftEncoderSpeed);
    val rRPM = getRPM(Consts.DriveTrain.CIRCUMFERENCE, DriveTrain.rightEncoderSpeed);

    override fun isFinished(): Boolean {
        return false
    }
}