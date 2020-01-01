package frc.team3324.robot.drivetrain.commands.teleop

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.command.InstantCommand
import frc.team3324.robot.drivetrain.DriveTrain.shifterStatus

class ShiftGears: InstantCommand() {
    override fun initialize() {
        shifterStatus = when {
            (shifterStatus == DoubleSolenoid.Value.kForward) -> DoubleSolenoid.Value.kReverse
            else -> DoubleSolenoid.Value.kForward
        }
    }
}