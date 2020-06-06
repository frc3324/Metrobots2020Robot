package frc.team3324.library.subsystems

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team3324.library.commands.MotorCommand

class MotorSubsystem(val motor: WPI_TalonSRX, val currentLimit: Int, val defaultSpeed: Double = 0.0): SubsystemBase() {
    init {
        motor.setNeutralMode(NeutralMode.Brake)
        motor.configContinuousCurrentLimit(currentLimit)
        motor.enableCurrentLimit(true)
    }

    var speed: Double
        get() = motor.get()
        set(input) = motor.set(input)
}