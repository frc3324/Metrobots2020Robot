package frc.team3324.robot

import edu.wpi.first.cameraserver.CameraServer
import edu.wpi.first.wpilibj.Compressor
import edu.wpi.first.wpilibj.PowerDistributionPanel
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.livewindow.LiveWindow
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.ScheduleCommand

import frc.team3324.robot.util.*

class Robot: TimedRobot() {
    private val compressor = Compressor()
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
        CommandScheduler.getInstance().schedule(robotContainer.getAutoCommand())
        SmartDashboard.putBoolean("We here 4", true)
        enabledInit()
    }
    override fun teleopInit() {
        enabledInit()
    }

    override fun teleopPeriodic() {
    }
}
