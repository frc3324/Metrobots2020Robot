package frc.team3324.robot.util

import edu.wpi.first.wpilibj.Relay
import edu.wpi.first.wpilibj2.command.InstantCommand

class SwitchRelay(val relay: Relay): InstantCommand() {

    override fun execute() {
        if (relay.get() == Relay.Value.kOff) {
            relay.set(Relay.Value.kOn)
        } else {
            relay.set(Relay.Value.kOff)
        }
    }
}