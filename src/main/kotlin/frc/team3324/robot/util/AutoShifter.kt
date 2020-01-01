package frc.team3324.robot.util

import edu.wpi.first.wpilibj.Notifier
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team3324.robot.drivetrain.DriveTrain
import frc.team3324.robot.drivetrain.commands.teleop.Drive
import java.sql.Time
import kotlin.math.sign


object DataCollector: Command() {
    var lastTime = 0.0
    var lastPosition = 0.0
    var lastSpeed = 0.0
    var lastShift = 0.0
    var enabled = true

    private val notifier = Notifier(::run)

    override fun initialize() {
        DriveTrain.shifterStatus = Consts.DriveTrain.LOW_GEAR
        notifier.startPeriodic(0.15)
    }
    private fun run() {
        var timeDifference = Timer.getFPGATimestamp() - lastTime

        val timeBetweenShifts = Timer.getFPGATimestamp() - lastShift
        lastTime = Timer.getFPGATimestamp()

        if (timeDifference > 0.5) {
            timeDifference = 0.2
        }

        DriveTrain.speed = Math.abs((DriveTrain.leftEncoderSpeed + DriveTrain.rightEncoderSpeed) / 2)
        SmartDashboard.putNumber("Speed", DriveTrain.speed)
        val shifterStatus = DriveTrain.shifterStatus

        if (sign(DriveTrain.getLeftEncoderDistance()) * sign(DriveTrain.getRightEncoderDistance()) != -1.0 && enabled) {
            if (shifterStatus == Consts.DriveTrain.LOW_GEAR && DriveTrain.speed > (Consts.DriveTrain.LOW_GEAR_MAX_VELOCITY * 0.7 ) && timeBetweenShifts > 0.5) {
                DriveTrain.shifterStatus = Consts.DriveTrain.HIGH_GEAR
                lastShift = Timer.getFPGATimestamp()
            } else if (shifterStatus == Consts.DriveTrain.HIGH_GEAR && DriveTrain.speed < (Consts.DriveTrain.LOW_GEAR_MAX_VELOCITY * 0.5) && timeBetweenShifts > 0.5) {
                lastShift = Timer.getFPGATimestamp()
                DriveTrain.shifterStatus = Consts.DriveTrain.LOW_GEAR
            }
        }

    }

    override fun isFinished(): Boolean {
        return false
    }
}