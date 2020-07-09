package frc.team3324.library.subsystems

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj2.command.SubsystemBase

open class MotorSubsystem(val motorMap: Map<String, WPI_TalonSRX>, val currentLimit: Int, val defaultSpeed: Double = 0.0): SubsystemBase() {
    init {
        for (motor in motorMap.values) {
            motor.setNeutralMode(NeutralMode.Brake)
            motor.configContinuousCurrentLimit(currentLimit)
            motor.enableCurrentLimit(true)
        }
    }

    fun setSpeed(motorName: String, speed: Double) {
        motorMap.get(motorName)?.set(speed)
    }

    fun getMotor(motorName: String): WPI_TalonSRX? {
        return motorMap.get(motorName)
    }
}