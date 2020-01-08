package frc.team3324.robot

import edu.wpi.first.cameraserver.CameraServer
import edu.wpi.first.wpilibj.Compressor
import edu.wpi.first.wpilibj.PowerDistributionPanel
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.livewindow.LiveWindow
import edu.wpi.first.wpilibj2.command.CommandScheduler

import frc.team3324.robot.util.*

class Robot: TimedRobot() {
    private val compressor = Compimressor(1)
    val robotContainer = RobotContainer()

    companion object {
        val pdp = PowerDistributionPanel()
    }

    override fun robotInit() {
        LiveWindow.disableAllTelemetry()

        compressor.start()

    }

    fun enabledInit() {
    }

    override fun robotPeriodic() {
        CommandScheduler.getInstance().run()
    }

    override fun autonomousInit() {
        enabledInit()
    }
    override fun teleopInit() {
        enabledInit()
    }

    override fun teleopPeriodic() {
    }
}
