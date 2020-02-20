package frc.team3324.robot.climber

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team3324.robot.util.Consts

class Climber: SubsystemBase() {
    private val leftMotor = WPI_TalonSRX(Consts.Climber.MOTOR_LEFT)
    private val rightMotor = WPI_TalonSRX(Consts.Climber.MOTOR_RIGHT)

    init {
        leftMotor.setNeutralMode(NeutralMode.Brake)
        rightMotor.setNeutralMode(NeutralMode.Brake)
        leftMotor.inverted = true
    }
    var leftSpeed: Double
        get() = leftMotor.get()
        set(x) = leftMotor.set(x)

    var rightSpeed: Double
        get() = rightMotor.get()
        set(x) = rightMotor.set(x)
}