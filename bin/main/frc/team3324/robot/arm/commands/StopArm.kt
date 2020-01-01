package frc.team3324.robot.arm.commands

import edu.wpi.first.wpilibj.command.InstantCommand
import frc.team3324.robot.arm.Arm
import frc.team3324.robot.util.OI

class StopArm: InstantCommand() {
    init {
       requires(Arm)
    }

    override fun initialize() {
        OI.oneEightyDegree.stopNotifier()
        OI.zeroDegree.stopNotifier()
    }
}