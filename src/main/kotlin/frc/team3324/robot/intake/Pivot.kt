package frc.team3324.robot.intake

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import com.revrobotics.SparkMax
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import io.github.oblarg.oblog.Loggable
import io.github.oblarg.oblog.annotations.Log

class Pivot: SubsystemBase(), Loggable {
    private val pivotMotor = CANSparkMax(8, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val pivotEncoder = pivotMotor.encoder

    val encoderPosition: Double
        @Log
        get() = pivotEncoder.position

    val encoderVelocity: Double
        @Log
        get() = pivotEncoder.velocity

    init {
        pivotMotor.idleMode = CANSparkMax.IdleMode.kBrake
        pivotEncoder.position = 0.0
        pivotMotor.setSmartCurrentLimit(30)
    }

    var pivot: Double
        @Log
        get() = pivotMotor.get()
        set(x) = pivotMotor.set(x)

    override fun periodic() {
    }
}