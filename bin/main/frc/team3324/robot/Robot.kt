package frc.team3324.robot

import com.revrobotics.CANSparkMax
import edu.wpi.first.cameraserver.CameraServer
import edu.wpi.first.wpilibj.Compressor
import edu.wpi.first.wpilibj.PowerDistributionPanel
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.command.Scheduler
import edu.wpi.first.wpilibj.livewindow.LiveWindow
import edu.wpi.first.wpilibj.Threads
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team3324.robot.arm.Arm
import frc.team3324.robot.arm.commands.ArmTracker

import frc.team3324.robot.drivetrain.DriveTrain
import frc.team3324.robot.drivetrain.commands.teleop.Drive
import frc.team3324.robot.intake.cargo.Cargo
import frc.team3324.robot.intake.hatch.Hatch
import frc.team3324.robot.util.*

class Robot : TimedRobot() {
    private val compressor = Compressor(1)

    companion object {
        val pdp = PowerDistributionPanel()
    }

    override fun robotInit() {
        LiveWindow.disableAllTelemetry()

        compressor.start()

        Camera
        Drive
        DriveTrain
        OI
        Arm
        Hatch
        Cargo
        LED

        LED.redStatus = true
        LED.blueStatus = true
        LED.greenStatus = true


        CameraServer.getInstance().startAutomaticCapture(1)
        CameraServer.getInstance().startAutomaticCapture(0)

        CameraServer.getInstance().putVideo("Camera output", 240, 144)
        Threads.setCurrentThreadPriority(true, 40)
        Thread.sleep(10000)
    }

    fun enabledInit() {
       Scheduler.getInstance().add(Camera)
    }

    override fun robotPeriodic() {
        Scheduler.getInstance().run()
        ArmTracker.run()
    }

    override fun autonomousInit() {
        enabledInit()
        DriveTrain.shifterStatus = Consts.DriveTrain.HIGH_GEAR
    }
    override fun teleopInit() {
        enabledInit()
//        Scheduler.getInstance().add(DataCollector)
    }

    override fun teleopPeriodic() {
    }
}
