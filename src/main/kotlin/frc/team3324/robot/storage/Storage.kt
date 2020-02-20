package frc.team3324.robot.storage

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team3324.robot.util.Consts

class Storage(): SubsystemBase() {
    private val motorTop = WPI_TalonSRX(Consts.Storage.MOTOR_TOP)
    private val motorBot = WPI_TalonSRX(Consts.Storage.MOTOR_BOTTOM)

    init {
        motorTop.configFactoryDefault()
        motorBot.configFactoryDefault()
        configureBrakeMode()

        motorTop.inverted = true
        motorBot.inverted = false

        motorTop.enableCurrentLimit(true)
        motorTop.configContinuousCurrentLimit(20)
        motorBot.enableCurrentLimit(true)
        motorBot.configContinuousCurrentLimit(20)
    }

    var topSpeed: Double
        get() = motorTop.get()
        set(x) = motorTop.set(x)

    var botSpeed: Double
        get() = motorBot.get()
        set(x) = motorBot.set(x)

    private fun configureBrakeMode() {
        motorBot.setNeutralMode(NeutralMode.Brake)
        motorTop.setNeutralMode(NeutralMode.Brake)
    }
}