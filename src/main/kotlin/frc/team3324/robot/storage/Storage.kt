package frc.team3324.robot.storage

import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team3324.library.motorcontrollers.MetroTalonSRX
import frc.team3324.library.motorcontrollers.SmartMotorController

class Storage(val motorTop: MetroTalonSRX, val motorBot: MetroTalonSRX): SubsystemBase() {

    init {
        motorTop.configFactoryDefault()
        motorBot.configFactoryDefault()
        configureBrakeMode()

        motorTop.inverted = false
        motorBot.inverted = false

        motorTop.enableCurrentLimit(true)
        motorTop.configContinuousCurrentLimit(30)
        motorBot.enableCurrentLimit(true)
        motorBot.configContinuousCurrentLimit(30)
    }

    var topSpeed: Double
        get() = motorTop.get()
        set(x) = motorTop.set(x)

    var botSpeed: Double
        get() = motorBot.get()
        set(x) = motorBot.set(x)

    private fun configureBrakeMode() {
        motorBot.setNeutralMode(SmartMotorController.MetroNeutralMode.BRAKE)
        motorTop.setNeutralMode(SmartMotorController.MetroNeutralMode.BRAKE)
    }
}