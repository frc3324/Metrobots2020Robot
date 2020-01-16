package frc.team3324.robot.shooter

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team3324.robot.util.Consts
import frc.team3324.robot.util.physics.Motors
import frc.team3324.robot.util.physics.Motors.getPercentFromRPM
import frc.team3324.robot.util.physics.Motors.getVoltageFromRPM

class Shooter: SubsystemBase() {
    val leftMotor = CANSparkMax(Consts.Shooter.LEFT_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless)
    val rightMotor = CANSparkMax(Consts.Shooter.RIGHT_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless)
    val leftEncoder = leftMotor.encoder
    val rightEncoder = rightMotor.encoder
    val motor = Motors.Neo(2)

    var RPM: Double
        get() = (-leftEncoder.velocity + rightEncoder.velocity) / 2.0
        set(rpm) = leftMotor.set(0.5)//getVoltageFromRPM(rpm / Consts.Shooter.GEAR_RATIO, motor))

    init {
        rightMotor.restoreFactoryDefaults()
        leftMotor.restoreFactoryDefaults()
        rightMotor.idleMode = CANSparkMax.IdleMode.kBrake
        leftMotor.idleMode = CANSparkMax.IdleMode.kBrake

//        rightMotor.follow(leftMotor)
        rightMotor.inverted = true
        leftEncoder.velocityConversionFactor = Consts.Shooter.GEAR_RATIO
        rightEncoder.velocityConversionFactor = Consts.Shooter.GEAR_RATIO
        leftMotor.setSmartCurrentLimit(40)
        rightMotor.setSmartCurrentLimit(40)
    }

    override fun periodic() {
        leftMotor.setVoltage(12.0)
        SmartDashboard.putNumber("RPM ", leftEncoder.velocity)
        SmartDashboard.putNumber("Amp", leftMotor.outputCurrent)
    }

}