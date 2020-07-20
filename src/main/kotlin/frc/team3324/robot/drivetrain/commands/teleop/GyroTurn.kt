package frc.team3324.robot.drivetrain.commands.teleop


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.Subsystem
import frc.team3324.robot.Robot
import kotlin.math.abs
import kotlin.math.sign

class GyroTurn(subsystem: Subsystem, private val kP: Double, private val kS: Double, private var setPointMethod: () -> Double, val output: (Double) -> Unit):CommandBase() {
    init {
        addRequirements(subsystem)
    }

    override fun initialize() {
        Robot.light.set(true)
    }
    override fun execute() {
        var error = setPointMethod()
        val speed = -error * kP

        SmartDashboard.putNumber("Speed from gyro turn", speed)
        SmartDashboard.putNumber("Error", error)

        output(speed + (sign(speed) * kS))
    }

    override fun end(interrupted: Boolean) {
        output(0.0)
    }

    override fun isFinished(): Boolean {
        return false
    }
}