package frc.team3324.library.commands

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj2.command.InstantCommand

class PneumaticShiftCommand(val solenoid: DoubleSolenoid) : InstantCommand() {
    override fun execute() {
        solenoid.set(
                when (solenoid.get()) {
                    DoubleSolenoid.Value.kForward -> DoubleSolenoid.Value.kReverse
                    else -> DoubleSolenoid.Value.kReverse
                }
        )
    }
}