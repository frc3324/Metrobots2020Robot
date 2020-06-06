package frc.team3324.library.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.SubsystemBase

class PIDCommand(val kP: Double, val kI: Double, val kD: Double, val goal: Double, val dt: Double, val subsystem: SubsystemBase, val measurement: () -> Double, val useOutput: (Double) -> Unit): CommandBase() {
    private var integral = 0.0
    private var lastPosition = 0.0

    init {
        this.addRequirements(subsystem)
    }

    override fun initialize() {
        integral = 0.0
    }

    private fun executePID() {
        val position = measurement()
        val error = goal - position
        val deriv = position - lastPosition

        integral += error
        lastPosition = position

        val output = (error * kP) + (integral * kI) - (deriv * kD)
        useOutput(output)
    }

    override fun isFinished(): Boolean {
        return measurement() == goal // return true if error is 0
    }
}