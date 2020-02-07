package frc.team3324.robot.storage

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team3324.robot.util.Consts

class Storage: SubsystemBase() {
    val storageMotorBottom = WPI_TalonSRX(Consts.Storage.MOTOR_BOTTOM)
    val storageMotorTop = WPI_TalonSRX(Consts.Storage.MOTOR_TOP)

    init {
        storageMotorBottom.follow(storageMotorTop)
        storageMotorTop.enableCurrentLimit(true)
        storageMotorTop.configContinuousCurrentLimit(20)
    }

    var speed: Double
        get() = storageMotorTop.get()
        set(x) = storageMotorTop.set(x)
}