package frc.team3324.robot.drivetrain.commands.teleop

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.team3324.robot.drivetrain.DriveTrain

class ShiftGears(val driveTrain: DriveTrain): InstantCommand() {
    override fun initialize() {
        driveTrain.shifterStatus = when {
            (driveTrain.shifterStatus == DoubleSolenoid.Value.kForward) -> DoubleSolenoid.Value.kReverse
            else -> DoubleSolenoid.Value.kForward
        }
    }
}