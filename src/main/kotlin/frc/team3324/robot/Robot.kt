package frc.team3324.robot

import edu.wpi.first.cameraserver.CameraServer
import edu.wpi.first.wpilibj.Compressor
import edu.wpi.first.wpilibj.PowerDistributionPanel
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.livewindow.LiveWindow
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.ScheduleCommand
import edu.wpi.first.wpilibj.SendableBase
import edu.wpi.first.wpilibj.SensorUtil
import edu.wpi.first.wpilibj.Ultrasonic


import frc.team3324.robot.util.*

class Robot: TimedRobot() {
    private val compressor = Compressor()
    val robotContainer = RobotContainer()
    val ultrasonic = Ultrasonic(1,1);
    val rangeMMeters: Double
        get() = ultrasonic.rangeMM

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
        SmartDashboard.putNumber("MM Distance: ", rangeMMeters)
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
