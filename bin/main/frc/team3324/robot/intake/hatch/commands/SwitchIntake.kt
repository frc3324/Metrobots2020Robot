package frc.team3324.robot.intake.hatch.commands

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.command.InstantCommand
import frc.team3324.robot.intake.hatch.Hatch

class SwitchIntake: InstantCommand() {

    override fun initialize() {
        if (Hatch.intakeStatus == DoubleSolenoid.Value.kForward) {
            Hatch.intakeStatus = DoubleSolenoid.Value.kReverse
        } else {
            Hatch.intakeStatus = DoubleSolenoid.Value.kForward
        }
    }
}