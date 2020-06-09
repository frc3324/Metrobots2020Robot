package frc.team3324.libarry.commands

import edu.wpi.first.wpilibj.Notifier
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.Subsystem

class PIDCommand(val kP: Double, val kI: Double, val kD: Double, val goal: Double, val dt: Double, subsystem: Subsystem, val measurement: () -> Double, val useOutput: (Double) -> Unit): CommandBase() {
    private var integral = 0.0
    private var lastPosition = 0.0
    private val notifier = Notifier(this ::executePID)

    init {
        addRequirements(subsystem)
    }

    override fun initialize() {
        integral = 0.0
        notifier.startPeriodic(dt)
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

    override fun end(interrupted: Boolean) {
        stopNotifier()
    }

    fun stopNotifier() {
        notifier.stop()
        notifier.stop()
    }

    override fun isFinished(): Boolean {
        return false
    }

}
