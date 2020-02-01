package frc.team3324.robot

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.Relay
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.XboxController.Button
import edu.wpi.first.wpilibj.controller.RamseteController
import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.geometry.Translation2d
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint
import edu.wpi.first.wpilibj.util.Units
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.RamseteCommand
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.team3324.robot.drivetrain.DriveTrain
import frc.team3324.robot.drivetrain.commands.teleop.Drive
import frc.team3324.robot.drivetrain.commands.teleop.GyroTurn
import frc.team3324.robot.drivetrain.commands.teleop.ToggleAutoShifting
import frc.team3324.robot.intake.Intake
import frc.team3324.robot.intake.commands.RunIntake
import frc.team3324.robot.intake.commands.RunPivot
import frc.team3324.robot.shooter.Shooter
import frc.team3324.robot.shooter.commands.RunShooter
import frc.team3324.robot.util.Camera
import frc.team3324.robot.util.Consts
import frc.team3324.robot.util.PneumaticShift
import frc.team3324.robot.util.SwitchRelay
import io.github.oblarg.oblog.Logger
import java.util.function.BiConsumer
import java.util.function.Supplier

class RobotContainer {
    private val intake = Intake()
    private val driveTrain = DriveTrain()
    private val relay = Relay(0)
//    private val shooter = Shooter()
    private val primaryController = XboxController(0)
    private val secondaryController = XboxController(1)

    private val primaryRightX: Double
        get() = primaryController.getX(GenericHID.Hand.kLeft)
    private val primaryLeftY: Double
        get() = primaryController.getY(GenericHID.Hand.kRight)


   init {
       Logger.configureLoggingAndConfig(this, true)
       Camera.schedule()
       driveTrain.defaultCommand = Drive(driveTrain, {primaryController.getY(GenericHID.Hand.kLeft)}, {primaryController.getX(GenericHID.Hand.kRight)})
       intake.defaultCommand = RunPivot(intake, -0.05)
       configureButtonBindings()

   }

    fun configureButtonBindings() {
        JoystickButton(primaryController, Button.kBumperLeft.value).whenPressed(PneumaticShift(driveTrain.gearShifter))
        JoystickButton(primaryController, Button.kA.value).whenPressed(ToggleAutoShifting(driveTrain))
        JoystickButton(primaryController, Button.kX.value).whenPressed(SwitchRelay(relay))
        JoystickButton(primaryController, Button.kY.value).whenPressed(GyroTurn(
                1.0/45,
                Consts.DriveTrain.ksVolts/12,
                {SmartDashboard.getNumber("targetAngle", -1000.0)},
                driveTrain::yaw,
                {input -> driveTrain.curvatureDrive(0.0, input, true)}
        ))
        JoystickButton(secondaryController, Button.kBumperLeft.value).whileHeld(RunPivot(intake, 0.2))
        JoystickButton(secondaryController, Button.kBumperRight.value).whileHeld(RunPivot(intake, -0.2))
        JoystickButton(secondaryController, Button.kA.value).whileHeld(RunIntake(intake, -1.0))
//        JoystickButton(secondaryController, Button.kA.value).whenPressed(RunShooter(shooter, 5000.0))
    }

    fun getAutoCommand(): Command {
        val config = TrajectoryConfig(Consts.DriveTrain.LOW_GEAR_MAX_VELOCITY, Consts.DriveTrain.LOW_GEAR_MAX_ACCELERATION)
        config.setKinematics(driveTrain.driveKinematics)

        val start = Pose2d(Units.feetToMeters(0.0), Units.feetToMeters(0.0), Rotation2d(0.0))
        val end = Pose2d(Units.feetToMeters(1.0), Units.feetToMeters(0.0), Rotation2d(0.0))

        val interiorWaypoints = listOf(Translation2d(0.5, 0.0))
        val voltageConstraint = DifferentialDriveVoltageConstraint(driveTrain.feedForward, driveTrain.driveKinematics, 7.0)

        val trajectory = TrajectoryGenerator.generateTrajectory(
                start,
                interiorWaypoints,
                end,
                config)

        val ramseteCommand = RamseteCommand(
                trajectory,
                Supplier {driveTrain.pose},
                RamseteController(Consts.DriveTrain.kRamseteB, Consts.DriveTrain.kRamseteZeta),
                driveTrain.feedForward,
                driveTrain.driveKinematics,
                Supplier {driveTrain.wheelSpeeds},
                driveTrain.leftPIDController,
                driveTrain.rightPIDController,
                BiConsumer(driveTrain::tankDriveVolts),
                driveTrain)
        return ramseteCommand
    }
}