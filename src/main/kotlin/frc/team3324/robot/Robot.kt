package frc.team3324.robot

import edu.wpi.first.wpilibj.livewindow.LiveWindow
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandScheduler
import com.cuforge.libcu.Lasershark
import edu.wpi.first.cameraserver.CameraServer
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.*
import frc.team3324.robot.util.Camera
import io.github.oblarg.oblog.Logger

class Robot: TimedRobot() {

    private val compressor = Compressor()
    private val lidar = Lasershark(2)
    private val rangeMeters: Double
        get() = lidar.distanceMeters
    private val ultrasonic = AnalogInput(1)
    private val depRangeInches: Double
        get() = ultrasonic.value.toDouble() * 0.125

    companion object {
        val light = DigitalOutput(1)
        val robotContainer = RobotContainer()
        val pdp = PowerDistributionPanel()
    }

    override fun robotInit() {
        CameraServer.getInstance().startAutomaticCapture()
        LiveWindow.disableAllTelemetry()
        compressor.start()
    }

    fun enabledInit() {
    }

    override fun robotPeriodic() {
        CameraServer.getInstance().getVideo()
        CommandScheduler.getInstance().run()
        Logger.updateEntries()
        Logger.updateEntries()
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
