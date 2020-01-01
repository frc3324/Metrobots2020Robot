package frc.team3324.robot.arm.commands

import edu.wpi.first.wpilibj.command.Command
import frc.team3324.robot.arm.Arm
import frc.team3324.robot.util.OI

class ControlArm: Command() {

    init {
        requires(Arm)
    }

    override fun execute() {
        val leftY = OI.secondaryLeftY
        Arm.setSpeed(leftY)
    }

    override fun isFinished(): Boolean {
        return false
    }
}