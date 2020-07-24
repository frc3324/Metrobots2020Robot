package frc.team3324.robot.intake

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.DutyCycleEncoder
import frc.team3324.library.motorcontrollers.MetroTalonSRX
import frc.team3324.library.subsystems.MotorSubsystem
import frc.team3324.robot.util.Consts
import io.github.oblarg.oblog.Loggable
import io.github.oblarg.oblog.annotations.Log

class Intake: MotorSubsystem(listOf(Consts.Intake.MOTOR)), Loggable {
    private val dutyEncoder = DutyCycleEncoder(7)

    init {
        this.getMotor(0).inverted = true
    }

    private val radianMeasure: Double
        @Log
        get() = dutyEncoder.get()*2*Math.PI

    val current
        @Log
        get() = this.getMotor(0).getCurrentDraw()
}
