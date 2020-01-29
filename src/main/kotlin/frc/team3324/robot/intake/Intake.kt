package frc.team3324.robot.intake

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.DutyCycleEncoder
import frc.team3324.robot.util.Moggers

class Intake : SubsystemBase() {
    private val leftMotor = WPI_TalonSRX(0)
    private val rightMotor = WPI_TalonSRX(1)
    private val dutyEncoder = DutyCycleEncoder(7)
    private val radianMeasure: Double
        get() = dutyEncoder.get()*2*Math.PI

    init {
        leftMotor.configContinuousCurrentLimit(20)
        leftMotor.enableCurrentLimit(true)
        rightMotor.follow(leftMotor)

    }


    fun run(power: Double) {
        leftMotor.set(power)
        val motorCurrent = leftMotor.statorCurrent
        //SmartDashboard.putNumber("Motor Current: ", motorCurrent)
        Moggers.addToLog(motorCurrent, "Intake", "Motor Current: ")
    }

    override fun periodic() {
        /*SmartDashboard.putNumber("Radians", radianMeasure)
        SmartDashboard.putNumber("Raw Value", dutyEncoder.get())
        SmartDashboard.putNumber("Raw Freq", dutyEncoder.frequency.toDouble())*/
        Moggers.addToLog(radianMeasure, "Intake", "Radians: ")
        Moggers.addToLog(dutyEncoder.get(), "Intake", "Raw Value: ")
        Moggers.addToLog(dutyEncoder.frequency.toDouble(), "Intake", "Raw Freq: ")
    }

}