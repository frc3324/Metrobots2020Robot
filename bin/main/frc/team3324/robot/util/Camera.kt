package frc.team3324.robot.util

import edu.wpi.first.cameraserver.CameraServer
import edu.wpi.first.wpilibj.Notifier
import edu.wpi.first.wpilibj.command.Command

object Camera: Command() {
    private val notifier = Notifier(::run)

    override fun initialize() {
        notifier.startPeriodic(0.02)
    }

    fun run() {
        CameraServer.getInstance().video
    }

    override fun isFinished(): Boolean {
        return false
    }
}