package frc.team3324.robot.drivetrain.commands.teleop


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import kotlin.math.abs
import kotlin.math.sign

class GyroTurn(private val kP: Double, private val kS: Double, private val setPointMethod: () -> Double, val input: () -> Double, val output: (Double) -> Unit):CommandBase() {
    var setPoint = setPointMethod()
    private var offset = input()

    override fun initialize() {
        offset = input()
        setPoint = -setPointMethod() + offset
    }

    override fun execute() {
        SmartDashboard.putNumber("Setpoint", setPoint)
        SmartDashboard.putNumber("Boop", (1000.0 + offset))
        if (setPoint != (1000.0 + offset)) {
            val currentAngle = input()
            val error = setPoint - input()
            val speed = error * kP
            SmartDashboard.putNumber("Speed from gyro turn", speed)
            SmartDashboard.putNumber("Desired Angle", setPoint)
            SmartDashboard.putNumber("Current Angle", currentAngle)
            SmartDashboard.putNumber("Error", error)
            output(speed + (sign(speed) * kS))
        }
    }

    override fun end(interrupted: Boolean) {
        output(0.0)
    }

    override fun isFinished(): Boolean {
        SmartDashboard.putNumber("Ending Error", setPoint - input())
        return abs((setPoint - input())) < 1.0 || setPoint == (1000.0 + offset)
    }
}