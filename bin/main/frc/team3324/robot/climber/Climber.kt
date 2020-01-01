package frc.team3324.robot.climber

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.command.Subsystem
import frc.team3324.robot.util.Consts

object Climber: Subsystem() {
    val frontClimber = DoubleSolenoid(1, Consts.Climber.FRONT_FORWARD, Consts.Climber.FRONT_BACKWARD)
    val backClimber = DoubleSolenoid(Consts.Climber.BACK_FORWARD, Consts.Climber.BACK_BACKWARD)
    override fun initDefaultCommand() {

    }
}