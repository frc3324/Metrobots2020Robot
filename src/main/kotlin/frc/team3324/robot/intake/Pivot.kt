package frc.team3324.robot.intake

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj2.command.SubsystemBase

class Pivot: SubsystemBase() {
    private val pivotMotor = WPI_TalonSRX(26)

    var pivot: Double
        get() = pivotMotor.get()
        set(x) = pivotMotor.set(x)
}