package frc.team3324.library.subsystems

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team3324.library.motorcontrollers.SmartMotorController

open class MotorSubsystem(private val motorMap: Map<String, SmartMotorController>, val defaultSpeed: Double = 0.0): SubsystemBase() {
    init {
        for (motor in motorMap.values) {
            motor.setNeutralMode(SmartMotorController.MetroNeutralMode.BRAKE)
        }
    }

    fun setSpeed(motorName: String, speed: Double) {
        motorMap[motorName]?.set(speed)
    }

    fun getMotor(motorName: String): SmartMotorController? {
        return motorMap[motorName]
    }
}