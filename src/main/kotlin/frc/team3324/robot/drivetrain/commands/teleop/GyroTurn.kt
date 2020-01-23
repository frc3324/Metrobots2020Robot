package frc.team3324.robot.drivetrain.commands.teleop


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase

class GyroTurn(private val kP: Double, private val kS: Double, private val setPointMethod: () -> Double, val input: () -> Double, val output: (Double) -> Unit):CommandBase() {
    var setPoint = setPointMethod()
    private var offset = input()

    override fun initialize() {
        offset = input()
        setPoint = setPointMethod() + offset
    }

    override fun execute() {
        if (setPoint != (-1000.0 + offset)) {
            val currentAngle = input()
            val error = setPoint - (input() - offset)
            val speed = error * kP
            SmartDashboard.putNumber("Speed from gyro turn", speed)
            SmartDashboard.putNumber("Desired Angle", setPoint)
            SmartDashboard.putNumber("Current Angle", currentAngle)
            SmartDashboard.putNumber("Error", error)
            output(speed)
        } else {
            setPoint = setPointMethod()
        }
    }

    override fun end(interrupted: Boolean) {
        output(0.0)
    }

    override fun isFinished(): Boolean {
        SmartDashboard.putNumber("Ending Error", setPoint - input())
        return Math.abs((setPoint - input())) < 0.1
    }
}