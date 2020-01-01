package frc.team3324.robot.intake.hatch

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.command.Subsystem
import frc.team3324.robot.util.Consts

object Hatch: Subsystem() {

    val hatchIntake = DoubleSolenoid(Consts.HatchIntake.HATCH_INTAKE_PORT_FORWARD, Consts.HatchIntake.HATCH_INTAKE_PORT_BACKWARD)

    var intakeStatus: DoubleSolenoid.Value
        get() = hatchIntake.get()
        set(status) {
           hatchIntake.set(status)
        }

    override fun initDefaultCommand() {}
}