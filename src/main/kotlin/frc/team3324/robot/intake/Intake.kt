package frc.team3324.robot.intake

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.DutyCycleEncoder

class Intake : SubsystemBase() {
    private val leftMotor = WPI_TalonSRX(0)
    private val rightMotor = WPI_TalonSRX(1)
    private val dutyEncoder = DutyCycleEncoder(0)
    private var radianMeasure: Double = 0.0
        get() = ((dutyEncoder.get()-1)/1023)*2*Math.PI

    init {
        leftMotor.configContinuousCurrentLimit(20)
        leftMotor.enableCurrentLimit(true)
        rightMotor.follow(leftMotor)

    }


    fun run(power: Double) {
        leftMotor.set(power)
        val motorCurrent = leftMotor.statorCurrent
        SmartDashboard.putNumber("Motor Current: ", motorCurrent)
    }


}