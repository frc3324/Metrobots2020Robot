package frc.team3324.robot.intake

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.DutyCycleEncoder
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team3324.library.subsystems.MotorSubsystem
import io.github.oblarg.oblog.Loggable
import io.github.oblarg.oblog.annotations.Log

class Intake: MotorSubsystem(mapOf("leftMotor" to WPI_TalonSRX(26)), 10), Loggable {
    private val leftMotor = WPI_TalonSRX(26)
    private val dutyEncoder = DutyCycleEncoder(7)

    init {
        this.getMotor("leftMotor")?.inverted = true
    }

    private val radianMeasure: Double
        @Log
        get() = dutyEncoder.get()*2*Math.PI

    val current
        @Log
        get() = this.getMotor("leftMotor")?.statorCurrent
}
