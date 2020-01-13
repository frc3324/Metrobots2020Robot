package frc.team3324.robot.intake

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard

class Intake : SubsystemBase() {
    private val leftMotor = WPI_TalonSRX(0)
    private val rightMotor = WPI_TalonSRX(1)
    private var i = 0
    private var motorCurrent: Double = 0.0

    init {
        leftMotor.configContinuousCurrentLimit(20)
        leftMotor.enableCurrentLimit(true)
        rightMotor.follow(leftMotor)

    }


    fun run(power: Double) {
        leftMotor.set(power)
        motorCurrent = leftMotor.statorCurrent
        SmartDashboard.putData(motorCurrent.toString(), SmartDashboard.getData(i.toString()))
        i++
    }


}