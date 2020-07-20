package frc.team3324.library.motorcontrollers

import com.revrobotics.CANSparkMax

class MetroSparkMAX(deviceID: Int, type: MotorType, currentLimit: Int) : CANSparkMax(deviceID, type), SmartMotorController {
    init {
        setCurrentLimit(currentLimit);
    }

    override fun follow(motor: SmartMotorController, invert: Boolean) {
        super.follow(motor as CANSparkMax, invert)
    }

    override fun setCurrentLimit(value: Int) {
        super.setSmartCurrentLimit(value)
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