package frc.team3324.robot.shooter

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team3324.robot.util.Consts
import frc.team3324.robot.util.physics.Motors
import frc.team3324.robot.util.physics.Motors.getPercentFromRPM

class Shooter: SubsystemBase() {
    val leftMotor = CANSparkMax(Consts.Shooter.LEFT_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless)
    val rightMotor = CANSparkMax(Consts.Shooter.RIGHT_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless)
    val leftEncoder = leftMotor.encoder
    val rightEncoder = rightMotor.encoder
    val motor = Motors.Neo(2)

    var RPM: Double
        get() = leftEncoder.velocity
        set(rpm) = leftMotor.setVoltage(runPID(rpm))

    init {
        rightMotor.follow(leftMotor)
        leftEncoder.velocityConversionFactor = Consts.Shooter.GEAR_RATIO
        rightEncoder.velocityConversionFactor = Consts.Shooter.GEAR_RATIO
        leftMotor.setSmartCurrentLimit(40)
        rightMotor.setSmartCurrentLimit(40)
    }

    fun runPID(desiredSpeed: Double): Double {
        val error = desiredSpeed - RPM
        val pValue = Consts.Shooter.Kp * error
        return pValue + (desiredSpeed * Consts.Shooter.Kv) + Consts.Shooter.Ks
    }

    override fun periodic() {

    }

}