package frc.team3324.library.motorcontrollers

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel

class MetroSparkMAX(deviceID: Int, type: CANSparkMaxLowLevel.MotorType, currentLimit: Int) : CANSparkMax(deviceID, type), SmartMotorController {
    init {
        this.setSmartCurrentLimit(currentLimit);
    }

    override fun follow(motor: SmartMotorController, invert: Boolean) {
        this.follow(motor as CANSparkMax, invert)
    }

    override fun setCurrentLimit(value: Int) {
        this.setSmartCurrentLimit(value)
    }

    override fun getCurrentDraw(): Double {
        return this.outputCurrent
    }

    override fun setNeutralMode(mode: SmartMotorController.MetroNeutralMode) {
        this.idleMode = when (mode) {
            SmartMotorController.MetroNeutralMode.BRAKE -> IdleMode.kBrake
            SmartMotorController.MetroNeutralMode.COAST -> IdleMode.kCoast
        }
    }
}