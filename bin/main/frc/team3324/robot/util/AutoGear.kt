package frc.team3324.robot.util

import edu.wpi.first.wpilibj.Notifier
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.command.InstantCommand
import edu.wpi.first.wpilibj.command.StartCommand
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team3324.robot.drivetrain.DriveTrain
import frc.team3324.robot.drivetrain.commands.teleop.Drive
import java.sql.Time
import kotlin.math.sign

object AutoTransmission: Command() {


    var lastCheck = 0.0;
    var lastShift = 0.0;

    fun Initialize() {
        
    }

    override fun isFinished(): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}