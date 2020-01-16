package frc.team3324.robot.shooter

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team3324.robot.util.Consts
import frc.team3324.robot.util.physics.Motors
import frc.team3324.robot.util.physics.Motors.getPercentFromRPM
import io.github.oblarg.oblog.Loggable
import io.github.oblarg.oblog.annotations.Log

class Shooter: SubsystemBase(), Loggable {
    val leftMotor = CANSparkMax(Consts.Shooter.LEFT_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless)
    val rightMotor = CANSparkMax(Consts.Shooter.RIGHT_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless)
    val leftEncoder = leftMotor.encoder
    val rightEncoder = rightMotor.encoder

    val motor = Motors.Neo(2)

    val rightEncoderPosition
        @Log
        get() = rightEncoder.position
    val rightEncoderVelocity
        @Log
        get() = rightEncoder.velocity

    var rightMotorSpeed
        @Log
        get() = rightMotor.get()
        set(rightMotorSpeed) = rightMotor.set(rightMotorSpeed)
    val rightMotorCurrent
        @Log
        get() = rightMotor.outputCurrent

    val leftEncoderPosition
        @Log
        get() = leftEncoder.position
    val leftEncoderVelocity
        @Log
        get() = leftEncoder.velocity

    var leftMotorSpeed
        @Log
        get() = leftMotor.get()
        set(leftMotorSpeed) = leftMotor.set(leftMotorSpeed)
    val leftMotorCurrent
        @Log
        get() = leftMotor.outputCurrent

    var RPM: Double
        @Log
        get() = leftEncoder.velocity
        set(rpm) = leftMotor.setVoltage(runPID(rpm))

    init {
        rightMotor.restoreFactoryDefaults()
        leftMotor.restoreFactoryDefaults()
        rightMotor.idleMode = CANSparkMax.IdleMode.kCoast
        leftMotor.idleMode = CANSparkMax.IdleMode.kCoast
        rightMotor.follow(leftMotor, true)

        leftMotor.inverted = false
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
        SmartDashboard.putNumber("RPM ", (leftEncoder.velocity + rightEncoder.velocity) / 2.0)
        SmartDashboard.putNumber("Amp", leftMotor.outputCurrent)
    }

}