package frc.team3324.robot

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.XboxController.Button
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.controller.RamseteController
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward
import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.geometry.Translation2d
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.RamseteCommand
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.team3324.robot.drivetrain.DriveTrain
import frc.team3324.robot.drivetrain.commands.teleop.Drive
import frc.team3324.robot.drivetrain.commands.teleop.ShiftGears
import frc.team3324.robot.drivetrain.commands.teleop.ToggleAutoShifting
import frc.team3324.robot.intake.Intake
import frc.team3324.robot.intake.Run
import frc.team3324.robot.util.AutoShifter
import frc.team3324.robot.util.Camera
import frc.team3324.robot.util.Consts

class RobotContainer {
   // private val intake = Intake()
    private val driveTrain = DriveTrain()
    private val primaryController = XboxController(0)
    private val autoShifter = AutoShifter(driveTrain)

    private val primaryRightX: Double
        get() = primaryController.getX(GenericHID.Hand.kLeft)
    private val primaryLeftY: Double
        get() = primaryController.getY(GenericHID.Hand.kRight)


   init {
       Camera.schedule()
       driveTrain.defaultCommand = Drive(driveTrain, {primaryController.getY(GenericHID.Hand.kLeft)}, {primaryController.getX(GenericHID.Hand.kRight)})
       configureButtonBindings()

   }

    fun configureButtonBindings() {
        JoystickButton(primaryController, Button.kBumperLeft.value).whenPressed(ShiftGears(driveTrain))
        JoystickButton(primaryController, Button.kA.value).whenPressed(ToggleAutoShifting(autoShifter))
        //JoystickButton(primaryController, Button.kB.value).whenPressed(Run(intake))
    }

    fun getAutoCommand(): Command {
        var autoVoltageContraint = DifferentialDriveVoltageConstraint(SimpleMotorFeedforward(Consts.DriveTrain.ksVolts, Consts.DriveTrain.kvVoltSecondsPerMeter, Consts.DriveTrain.kaVoltSecondsSquaredPerMeter), driveTrain.driveKinematics, 7.0)

        val config = TrajectoryConfig(Consts.DriveTrain.HIGH_GEAR_MAX_VELOCITY, Consts.DriveTrain.HIGH_GEAR_MAX_ACCELERATION)
                .setKinematics(driveTrain.driveKinematics)
                .addConstraint(autoVoltageContraint)
        SmartDashboard.putBoolean("We here 1", true)
        val path = listOf(Translation2d(1.0, 1.0), Translation2d(2.0, -1.0))
        val trajectory = TrajectoryGenerator.generateTrajectory(
                Pose2d(0.0, 0.0, Rotation2d(0.0)),
                path,
                Pose2d(3.0, 0.0, Rotation2d(0.0)),
                config
        )
        SmartDashboard.putBoolean("We here", true)
        val ramseteCommand = RamseteCommand(
                trajectory,
                driveTrain::pose,
                RamseteController(Consts.DriveTrain.kRamseteB, Consts.DriveTrain.kRamseteZeta),
                SimpleMotorFeedforward(Consts.DriveTrain.ksVolts, Consts.DriveTrain.kvVoltSecondsPerMeter, Consts.DriveTrain.kaVoltSecondsSquaredPerMeter),
                driveTrain.driveKinematics,
                driveTrain::wheelSpeeds,
                PIDController(Consts.DriveTrain.kPDriveVel.toDouble(), 0.0, 0.0),
                PIDController(Consts.DriveTrain.kPDriveVel.toDouble(), 0.0, 0.0),
                driveTrain::tankDriveVolts,
                arrayOf(driveTrain))
        SmartDashboard.putBoolean("We here 3", true)
        driveTrain.safety = false

        return ramseteCommand
    }
}