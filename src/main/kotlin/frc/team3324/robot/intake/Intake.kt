package frc.team3324.robot.intake

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.DutyCycleEncoder
import io.github.oblarg.oblog.Loggable
import io.github.oblarg.oblog.annotations.Log

class Intake : SubsystemBase(), Loggable {
    private val leftMotor = WPI_TalonSRX(7)
    private val pivotMotor = WPI_TalonSRX(4)
    private val dutyEncoder = DutyCycleEncoder(7)

    var leftMotorSpeed
        @Log
        get() = leftMotor.get()
        set(LeftMotorSpeed) = leftMotor.set(leftMotorSpeed)

    val leftMotorCurrent
        @Log
        get() = leftMotor.statorCurrent


    private val radianMeasure: Double
        @Log
        get() = dutyEncoder.get()*2*Math.PI

    var pivot: Double
        get() = pivotMotor.get()
        set(x) = pivotMotor.set(x)

    init {
        leftMotor.inverted = true
        leftMotor.configContinuousCurrentLimit(20)
        leftMotor.enableCurrentLimit(true)
    }


    fun run(power: Double) {
        leftMotor.set(power)
    }

    override fun periodic() {
        SmartDashboard.putNumber("Radians", radianMeasure)
        SmartDashboard.putNumber("Raw Value", dutyEncoder.get())
        SmartDashboard.putNumber("Raw Freq", dutyEncoder.frequency.toDouble())
    }

}