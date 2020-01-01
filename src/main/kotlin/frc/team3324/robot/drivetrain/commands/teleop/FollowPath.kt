package frc.team3324.robot.drivetrain.commands.teleop

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team3324.robot.drivetrain.DriveTrain
import frc.team3324.robot.util.Consts
import frc.team3324.robot.util.physics.TrapezoidProfile


class FollowPath(val goal: TrapezoidProfile.State, val kp: Double, val kv: Double): Command() {
    init {
        requires(DriveTrain)
    }
    var timeStart = Timer.getFPGATimestamp()
    var encoderOffset = DriveTrain.getAverageDistance()
    val constraints = TrapezoidProfile.Constraints(Consts.DriveTrain.HIGH_GEAR_MAX_VELOCITY, Consts.DriveTrain.HIGH_GEAR_MAX_ACCELERATION)
    val path = TrapezoidProfile(constraints, goal)
    var timeDifference = Timer.getFPGATimestamp() - timeStart

    override fun initialize() {
        timeStart = Timer.getFPGATimestamp()
        encoderOffset = DriveTrain.getAverageDistance()
    }

    override fun execute() {
        var calculatedValue: Double
        timeDifference = Timer.getFPGATimestamp() - timeStart
        SmartDashboard.putNumber("time diff", timeDifference)
        val distance_covered: Double = DriveTrain.getAverageDistance() - encoderOffset
        val seg = path.calculate(timeDifference)
            val error = seg.position - distance_covered
            SmartDashboard.putNumber("error", error)
            calculatedValue = kp * error + (kv * seg.velocity) // V and A Terms
            SmartDashboard.putNumber("Correcter", kp * error)
            SmartDashboard.putNumber("FF", kv * seg.velocity)
            SmartDashboard.putBoolean("Done?", false)
            SmartDashboard.putNumber("Value", calculatedValue)

        SmartDashboard.putNumber("Total time", path.totalTime())
        DriveTrain.curvatureDrive(-calculatedValue, 0.0)

    }

    override fun isFinished(): Boolean {
        SmartDashboard.putBoolean("Done?", true)
        return path.isFinished(timeDifference)
    }
}