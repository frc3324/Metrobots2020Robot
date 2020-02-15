package frc.team3324.robot.intake

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.DutyCycleEncoder
import io.github.oblarg.oblog.Loggable
import io.github.oblarg.oblog.annotations.Log

class Intake : SubsystemBase(), Loggable {
    private val leftMotor = WPI_TalonSRX(20)
    private val dutyEncoder = DutyCycleEncoder(7)

    var speed
        @Log
        get() = leftMotor.get()
        set(x) = leftMotor.set(x)

    val current
        @Log
        get() = leftMotor.statorCurrent


    private val radianMeasure: Double
        @Log
        get() = dutyEncoder.get()*2*Math.PI


    init {
        leftMotor.inverted = true
        leftMotor.configContinuousCurrentLimit(20)
        leftMotor.enableCurrentLimit(true)
    }

    override fun periodic() {
        SmartDashboard.putNumber("Radians", radianMeasure)
        SmartDashboard.putNumber("Raw Value", dutyEncoder.get())
        SmartDashboard.putNumber("Raw Freq", dutyEncoder.frequency.toDouble())
    }

}