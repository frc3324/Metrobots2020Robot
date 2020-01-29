package frc.team3324.robot.shooter

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team3324.robot.util.Consts
import frc.team3324.robot.util.physics.Motors
import frc.team3324.robot.util.physics.Motors.getPercentFromRPM
import frc.team3324.robot.util.physics.Motors.getVoltageFromRPM
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import frc.team3324.robot.util.Moggers


class Shooter: SubsystemBase() {
    val leftMotor = CANSparkMax(Consts.Shooter.LEFT_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless)
    val rightMotor = CANSparkMax(Consts.Shooter.RIGHT_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless)
    val leftEncoder = leftMotor.encoder
    val rightEncoder = rightMotor.encoder
    val motor = Motors.Neo(2)
    val rpmChooser = SendableChooser<Double>()

    var RPM: Double
        get() = leftEncoder.velocity
        set(rpm) = leftMotor.setVoltage(runPID(rpm))
    var percentPower: Double
        get() = leftMotor.get()
        set(speed) = leftMotor.set(speed)

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

        rpmChooser.addOption("0", 0.0)
        rpmChooser.addOption("200", 200.0)
        rpmChooser.addOption("1000", 1000.0)
        rpmChooser.addOption("1500", 1500.0)
        rpmChooser.addOption("2000", 2000.0)
        rpmChooser.addOption("2500", 2500.0)
        rpmChooser.addOption("3000", 3000.0)
        rpmChooser.addOption("3500", 3500.0)
        rpmChooser.addOption("4000", 4000.0)
        rpmChooser.addOption("4500", 4500.0)
        rpmChooser.addOption("4750", 4750.0)
        rpmChooser.addOption("4800", 4800.0)
        rpmChooser.addOption("4875", 4875.0)
        rpmChooser.addOption("5250", 5250.0)
        rpmChooser.addOption("5500", 5500.0)
        rpmChooser.addOption("6000", 6000.0)
        rpmChooser.addOption("6500", 6500.0)
        rpmChooser.setDefaultOption("0", 0.0)
        Moggers.addChooser(rpmChooser, "Shooter", "RPM Chooser: ")
    }


    fun runPID(desiredSpeed: Double): Double {
        val error = desiredSpeed - RPM
        val pValue = Consts.Shooter.Kp * error
        return pValue + (desiredSpeed * Consts.Shooter.Kv) + Consts.Shooter.Ks
    }

    override fun periodic() {
        SmartDashboard.putNumber("RPM ", RPM)
        SmartDashboard.putNumber("Amp", leftMotor.outputCurrent)
        if (rpmChooser.selected != null) {
            RPM = rpmChooser.selected
        }
    }

}