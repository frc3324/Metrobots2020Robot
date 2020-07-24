package frc.team3324.library.subsystems

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team3324.library.motorcontrollers.SmartMotorController

open class MotorSubsystem(private val motorList: List<SmartMotorController>, val defaultSpeed: Double = 0.0): SubsystemBase() {
    init {
        motorList.forEach {
            it.setNeutralMode(SmartMotorController.MetroNeutralMode.BRAKE)
        }
    }

    fun setSpeed(speed: Double) {
        motorList.forEach {
            it.set(speed)
        }
    }

    fun setSpeed(speed: Double, index: Int) {
        motorList[index].set(speed)
    }

    fun getMotor(index: Int): SmartMotorController {
        return motorList[index]
    }
}