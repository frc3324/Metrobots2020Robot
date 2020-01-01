package frc.team3324.robot.arm.commands

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team3324.robot.arm.Arm
import frc.team3324.robot.drivetrain.DriveTrain

object ArmTracker {
    var lastVelocity = 0.0
    var lastTime: Double = Timer.getFPGATimestamp()



    fun run() {
        SmartDashboard.putNumber("Drivetrain position", DriveTrain.getAverageDistance())
        val time = Timer.getFPGATimestamp()
        val timeDiff = time - lastTime
        if (timeDiff < 0.05) {
            Arm.acceleration = (Arm.velocity - lastVelocity) / timeDiff
        } else {
            Arm.acceleration = 0.0
        }
        lastTime = time
        lastVelocity = Arm.velocity
    }
}