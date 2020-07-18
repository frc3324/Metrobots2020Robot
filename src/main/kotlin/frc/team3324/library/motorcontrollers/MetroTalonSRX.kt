package frc.team3324.library.motorcontrollers

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX

class MetroTalonSRX(deviceNumber: Int, currentLimit: Int) : WPI_TalonSRX(deviceNumber), SmartMotorController {
    init {
        this.enableCurrentLimit(true)
        this.setCurrentLimit(currentLimit)
    }

    override fun follow(motor: SmartMotorController, invert: Boolean) {
        this.follow(motor as WPI_TalonSRX)
    }

    override fun setCurrentLimit(value: Int) {
        this.configContinuousCurrentLimit(value)
    }

    override fun getCurrentDraw(): Double {
        return this.statorCurrent
    }

    override fun setNeutralMode(mode: SmartMotorController.MetroNeutralMode) {
        this.setNeutralMode(
                when (mode) {
                    SmartMotorController.MetroNeutralMode.BRAKE -> NeutralMode.Brake
                    SmartMotorController.MetroNeutralMode.COAST -> NeutralMode.Coast
                }
        )
    }
}