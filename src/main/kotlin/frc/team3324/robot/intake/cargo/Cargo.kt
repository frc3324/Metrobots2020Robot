package frc.team3324.robot.intake.cargo

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.command.Subsystem
import frc.team3324.robot.intake.cargo.commands.Feedforward
import frc.team3324.robot.util.Consts

object Cargo: Subsystem() {
    private val motor = WPI_TalonSRX(Consts.CargoIntake.CARGO_INTAKE_MOTOR)

    init {
        motor.setNeutralMode(NeutralMode.Brake)
        motor.configContinuousCurrentLimit(5)
        motor.enableCurrentLimit(true)
    }

    var speed: Double
        get() = motor.get()
        set(input) = motor.set(input)

    override fun initDefaultCommand() {
        defaultCommand = Feedforward()
    }
}