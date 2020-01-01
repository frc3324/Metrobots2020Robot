package frc.team3324.robot.arm.commands

import edu.wpi.first.wpilibj.command.Command
import frc.team3324.robot.arm.Arm

class ResetArm: Command() {
    init {
        requires(Arm)
    }

    override fun execute() {
        Arm.setArmRawSpeed(-0.5)
    }

    override fun isFinished(): Boolean {
        return Arm.frontSwitchStatus
    }

    override fun end() {
        Arm.setSpeed(0.0)
    }

}