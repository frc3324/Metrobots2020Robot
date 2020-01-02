package frc.team3324.robot.util

import edu.wpi.first.wpilibj.Notifier
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.drivetrain.DriveTrain
import kotlin.math.sign


class AutoShifter(val driveTrain: DriveTrain): CommandBase() {
    var lastTime = 0.0
    var lastShift = 0.0
    var enabled = true

    private val notifier = Notifier(::run)

    override fun initialize() {
        driveTrain.shifterStatus = Consts.DriveTrain.LOW_GEAR
        notifier.startPeriodic(0.15)
    }
    private fun run() {
        var timeDifference = Timer.getFPGATimestamp() - lastTime

        val timeBetweenShifts = Timer.getFPGATimestamp() - lastShift
        lastTime = Timer.getFPGATimestamp()

        if (timeDifference > 0.5) {
            timeDifference = 0.2
        }

        driveTrain.speed = Math.abs((driveTrain.leftEncoderSpeed + driveTrain.rightEncoderSpeed) / 2)
        SmartDashboard.putNumber("DT Speed", driveTrain.speed)
        val shifterStatus = driveTrain.shifterStatus

        if (sign(driveTrain.getLeftEncoderDistance()) * sign(driveTrain.getRightEncoderDistance()) != -1.0 && enabled) {
            if (shifterStatus === Consts.DriveTrain.LOW_GEAR && driveTrain.speed > (Consts.DriveTrain.LOW_GEAR_MAX_VELOCITY * 0.7 ) && timeBetweenShifts > 0.5) {
                driveTrain.shifterStatus = Consts.DriveTrain.HIGH_GEAR
                lastShift = Timer.getFPGATimestamp()
            } else if (shifterStatus == Consts.DriveTrain.HIGH_GEAR && driveTrain.speed < (Consts.DriveTrain.LOW_GEAR_MAX_VELOCITY * 0.5) && timeBetweenShifts > 0.5) {
                lastShift = Timer.getFPGATimestamp()
                driveTrain.shifterStatus = Consts.DriveTrain.LOW_GEAR
            }
        }

    }

    override fun isFinished(): Boolean {
        return false
    }
}