package frc.team3324.robot

import edu.wpi.first.wpilibj.Compressor
import edu.wpi.first.wpilibj.PowerDistributionPanel
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.livewindow.LiveWindow
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj.AnalogInput
import com.cuforge.libcu.Lasershark
import io.github.oblarg.oblog.Logger

class Robot: TimedRobot() {
    private val compressor = Compressor()
    val robotContainer = RobotContainer()
    val lidar = Lasershark(2)
    val rangeMeters: Double
        get() = lidar.distanceMeters
    val ultrasonic = AnalogInput(1)
    val depRangeInches: Double
        get() = ultrasonic.value.toDouble() * 0.125

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
        Logger.updateEntries()

        SmartDashboard.putNumber("Ultrasonic Inch Distance: ", depRangeInches)
        SmartDashboard.putNumber("Lidar Meter Distance: ", rangeMeters)
        Logger.updateEntries()
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
