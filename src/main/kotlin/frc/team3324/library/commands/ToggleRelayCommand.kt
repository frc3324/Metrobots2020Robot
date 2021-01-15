package frc.team3324.library.commands

import edu.wpi.first.wpilibj.Relay
import edu.wpi.first.wpilibj2.command.InstantCommand

class ToggleRelayCommand(val relay: Relay) : InstantCommand() {

    override fun execute() {
        relay.set(
                when (relay.get()) {
                    Relay.Value.kOn -> Relay.Value.kOff
                    else -> Relay.Value.kOn
                }
        )
    }
}