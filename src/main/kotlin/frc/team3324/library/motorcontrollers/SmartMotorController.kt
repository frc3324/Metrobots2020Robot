package frc.team3324.library.motorcontrollers

import com.revrobotics.CANSparkMax
import edu.wpi.first.wpilibj.SpeedController

interface SmartMotorController: SpeedController {
    enum class MetroNeutralMode {BRAKE, COAST}
    fun setCurrentLimit(value: Int)
    fun getCurrentDraw(): Double
    fun follow(motor: SmartMotorController, invert: Boolean)
    fun setNeutralMode(mode: MetroNeutralMode)
}