package frc.team3324.library.commands

import edu.wpi.first.wpilibj.Relay
import edu.wpi.first.wpilibj2.command.InstantCommand

class ToggleRelayCommand(val relay: Relay): InstantCommand() {

    override fun execute() {
        if (relay.get() == Relay.Value.kOn) {
            relay.set(Relay.Value.kOn)
        } else {
            relay.set(Relay.Value.kOff)
        }
    }
}