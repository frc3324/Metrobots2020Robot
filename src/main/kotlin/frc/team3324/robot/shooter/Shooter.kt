package frc.team3324.robot.shooter

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team3324.robot.util.Consts
import io.github.oblarg.oblog.Loggable
import io.github.oblarg.oblog.annotations.Log
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import frc.team3324.library.motorcontrollers.MetroSparkMAX
import frc.team3324.library.subsystems.MotorSubsystem

class Shooter(val leftMotor: MetroSparkMAX, val rightMotor: MetroSparkMAX): MotorSubsystem(listOf(leftMotor, rightMotor)), Loggable {
        private val leftEncoder = leftMotor.encoder
        private val rightEncoder = rightMotor.encoder

        private val rpmChooser = SendableChooser<Double>()

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

        var percentPower: Double
            get() = leftMotor.get()
            set(speed) = leftMotor.set(speed)

        init {
            SmartDashboard.putNumber("Numbah", 0.0)
            rightMotor.follow(leftMotor as CANSparkMax, true)

            leftMotor.inverted = true
            leftEncoder.velocityConversionFactor = Consts.Shooter.GEAR_RATIO
            rightEncoder.velocityConversionFactor = Consts.Shooter.GEAR_RATIO

            rpmChooser.addOption("0", 0.0)
            rpmChooser.addOption("200", 200.0)
            rpmChooser.addOption("1000", 1000.0)
            rpmChooser.addOption("1500", 1500.0)
            rpmChooser.addOption("2000", 2000.0)
            rpmChooser.addOption("2500", 2500.0)
            rpmChooser.addOption("3000", 3000.0)
            rpmChooser.addOption("3500", 3500.0)
            rpmChooser.addOption("4000", 4000.0)
            rpmChooser.addOption("4100", 4100.0)
            rpmChooser.addOption("4375", 4375.0)
            rpmChooser.addOption("4500", 4500.0)
            rpmChooser.addOption("4600", 4600.0)
            rpmChooser.addOption("4700", 4700.0)
            rpmChooser.addOption("4800", 4800.0)
            rpmChooser.addOption("4875", 4875.0)
            rpmChooser.addOption("5250", 5250.0)
            rpmChooser.addOption("5500", 5500.0)
            rpmChooser.addOption("6000", 6000.0)
            rpmChooser.addOption("6500", 6500.0)
            rpmChooser.addOption("7000", 7000.0)
            rpmChooser.setDefaultOption("0", 0.0)
            SmartDashboard.putData(rpmChooser)
        }

        fun runPID(desiredSpeed: Double): Double {
            val error = desiredSpeed - RPM
            val pValue = Consts.Shooter.Kp * error
            return -(pValue + (desiredSpeed * Consts.Shooter.Kv) + Consts.Shooter.Ks)
        }

        override fun periodic() {
            RPM = SmartDashboard.getNumber("Numbah", 0.0)
        }
    }