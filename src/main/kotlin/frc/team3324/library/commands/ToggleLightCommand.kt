package frc.team3324.library.commands

import edu.wpi.first.wpilibj.DigitalOutput
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.InstantCommand

class ToggleLightCommand(val light: DigitalOutput): InstantCommand() {
    override fun execute() {
        light.set(!light.get())
    }
}