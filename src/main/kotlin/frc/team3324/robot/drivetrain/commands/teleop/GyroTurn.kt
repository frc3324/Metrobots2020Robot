package frc.team3324.robot.drivetrain.commands.teleop


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import kotlin.math.abs
import kotlin.math.sign

class GyroTurn(private val kP: Double, private val kS: Double, private var setPoint: () -> Double, val input: () -> Double, val output: (Double) -> Unit):CommandBase() {
    private var offset = input()

    override fun initialize() {
        offset = input()
    }

    override fun execute() {
        val currentAngle = input()
        val error = setPoint() + currentAngle
        val speed = error * kP

        SmartDashboard.putNumber("Offset", (0.0 + offset))
        SmartDashboard.putNumber("Speed from gyro turn", speed)
        SmartDashboard.putNumber("Desired Angle", setPoint())
        SmartDashboard.putNumber("Current Angle", currentAngle)
        SmartDashboard.putNumber("Error", error)

        output(speed + (sign(speed) * kS))
    }

    override fun end(interrupted: Boolean) {
        output(0.0)
    }

    override fun isFinished(): Boolean {
        SmartDashboard.putNumber("Ending Error", setPoint() - input())
        return abs((setPoint() - input())) < 1.0 || setPoint() == (0.0 + offset)
    }
}