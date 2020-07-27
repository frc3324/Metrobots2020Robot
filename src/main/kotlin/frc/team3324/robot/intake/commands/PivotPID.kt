package frc.team3324.robot.intake.commands

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.intake.Pivot

class PivotPID(val pivot: Pivot, val goal: Double): CommandBase() {
    init {
        addRequirements(pivot)
    }

    override fun execute() {
        val error = goal - pivot.encoderPosition
        SmartDashboard.putNumber("Pivot Error", error)
        SmartDashboard.putNumber("Pivot speed", error * (-1/90.0) + 0.05)
        pivot.setSpeed(error * (-1/90.0) + 0.05)
    }

    override fun end(interrupted: Boolean) {
        pivot.setSpeed(0.0)
    }
}