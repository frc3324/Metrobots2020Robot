package frc.team3324.robot.storage

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team3324.robot.util.Consts

class Storage: SubsystemBase() {
    val motorTop = WPI_TalonSRX(Consts.Storage.MOTOR_TOP)
    val motorBottom = WPI_TalonSRX(Consts.Storage.MOTOR_BOTTOM)

    init {
        motorTop.configFactoryDefault()
        motorBottom.configFactoryDefault()
        configureBrakeMode()

        motorBottom.inverted = true

        motorBottom.follow(motorTop)
        motorTop.enableCurrentLimit(true)
        motorTop.configContinuousCurrentLimit(20)
    }

    var speed: Double
        get() = motorTop.get()
        set(x) = motorTop.set(x)

    private fun configureBrakeMode() {
        motorBottom.setNeutralMode(NeutralMode.Brake)
        motorTop.setNeutralMode(NeutralMode.Brake)
    }
}