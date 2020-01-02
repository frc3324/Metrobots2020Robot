package frc.team3324.robot.util

import edu.wpi.first.wpilibj2.command.CommandBase

object AutoTransmission: CommandBase() {


    var lastCheck = 0.0;
    var lastShift = 0.0;

    fun Initialize() {
        
    }

    override fun isFinished(): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}