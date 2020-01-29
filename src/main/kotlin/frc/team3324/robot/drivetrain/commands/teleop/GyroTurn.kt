package frc.team3324.robot.drivetrain.commands.teleop


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.util.Moggers
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
        Moggers.addToLog(setPoint, "GyroTurn", "SetPoint: ")
        Moggers.addToLog(1000.0 + offset, "GyroTurn", "Boop: ")
        if (setPoint != (1000.0 + offset)) {
            val currentAngle = input()
            val error = setPoint - input()
            val speed = error * kP
            Moggers.addToLog(speed, "GyroTurn", "Gyro Turn Speed: ")
            Moggers.addToLog(setPoint, "GyroTurn", "Desired Angle: ")
            Moggers.addToLog(currentAngle, "GyroTurn", "Current Angle: ")
            Moggers.addToLog(error, "GyroTurn", "Error: ")
            output(speed + (sign(speed) * kS))
        }
    }

    override fun end(interrupted: Boolean) {
        output(0.0)
    }

    override fun isFinished(): Boolean {
        Moggers.addToLog(setPoint - input(), "GyroTurn", "Ending Error: ")
        return abs((setPoint - input())) < 1.0 || setPoint == (1000.0 + offset)
    }
}