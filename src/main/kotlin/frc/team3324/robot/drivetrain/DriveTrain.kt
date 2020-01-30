package frc.team3324.robot.drivetrain

import com.kauailabs.navx.frc.AHRS
import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.*

import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase

import frc.team3324.robot.util.Consts

class DriveTrain: SubsystemBase() {

    val driveKinematics = DifferentialDriveKinematics(Consts.DriveTrain.DISTANCE_BETWEEN_WHEELS)
    val gearShifter = DoubleSolenoid(Consts.DriveTrain.GEARSHIFTER_FORWARD, Consts.DriveTrain.GEARSHIFTER_REVERSE)
    var activeConversionRatio: Double = Consts.DriveTrain.DISTANCE_PER_PULSE_LOW
    var shifterStatus: DoubleSolenoid.Value
        get() = gearShifter.get()
        set(status) {
            gearShifter.set(status)
        }

    val leftEncoderSpeed: Double
        get() = leftEncoder.velocity * (1/60.0) * activeConversionRatio
    val leftEncoderPosition: Double
        get() = leftEncoder.position * activeConversionRatio
    val rightEncoderSpeed: Double
        get() = rightEncoder.velocity * (1/60.0) * activeConversionRatio
    val rightEncoderPosition: Double
        get() = rightEncoder.position * activeConversionRatio
    val velocity: Double
        get() = (rightEncoderSpeed - leftEncoderSpeed) / 2.0
    val position: Double
        get() = (rightEncoderPosition - leftEncoderPosition) / 2.0
    val yaw: Double
        get() = -gyro.yaw.toDouble()

    val pose: Pose2d
        get() = diffDriveOdometry.poseMeters
    val wheelSpeeds: DifferentialDriveWheelSpeeds
        get() = DifferentialDriveWheelSpeeds(leftEncoderSpeed, rightEncoderSpeed)




    private val gyro = AHRS(SPI.Port.kMXP)

    private val lmMotor = CANSparkMax(Consts.DriveTrain.LM_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val luMotor = CANSparkMax(Consts.DriveTrain.LU_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val ldMotor = CANSparkMax(Consts.DriveTrain.LD_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless)

    private val rmMotor = CANSparkMax(Consts.DriveTrain.RM_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val ruMotor = CANSparkMax(Consts.DriveTrain.RU_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val rdMotor = CANSparkMax(Consts.DriveTrain.RD_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless)

    private val rightEncoder = rmMotor.encoder
    private val leftEncoder = lmMotor.encoder

    var safety: Boolean
        get() = drive.isSafetyEnabled
        set(bool) = drive.setSafetyEnabled(bool)

    private val drive = DifferentialDrive(rmMotor, lmMotor)

    val diffDriveOdometry = DifferentialDriveOdometry(Rotation2d.fromDegrees(gyro.yaw.toDouble()))

    var enabled = true

    fun setBrakeMode() {
        rmMotor.idleMode = CANSparkMax.IdleMode.kBrake
        ruMotor.idleMode = CANSparkMax.IdleMode.kBrake
        rdMotor.idleMode = CANSparkMax.IdleMode.kBrake
        lmMotor.idleMode = CANSparkMax.IdleMode.kBrake
        luMotor.idleMode = CANSparkMax.IdleMode.kBrake
        ldMotor.idleMode = CANSparkMax.IdleMode.kBrake
    }

    init {
        shifterStatus = Consts.DriveTrain.LOW_GEAR
        lmMotor.restoreFactoryDefaults()
        luMotor.restoreFactoryDefaults()
        ldMotor.restoreFactoryDefaults()

        rmMotor.restoreFactoryDefaults()
        ruMotor.restoreFactoryDefaults()
        rdMotor.restoreFactoryDefaults()

        rightEncoder.position = 0.0
        leftEncoder.position = 0.0

        rmMotor.setSmartCurrentLimit(33)
        lmMotor.setSmartCurrentLimit(33)
        rmMotor.setSecondaryCurrentLimit(60.0)
        lmMotor.setSecondaryCurrentLimit(60.0)

        ruMotor.follow(rmMotor)
        rdMotor.follow(rmMotor)

        luMotor.follow(lmMotor)
        ldMotor.follow(lmMotor)

        ruMotor.inverted = false
        rmMotor.inverted= false
        rdMotor.inverted = false

        lmMotor.inverted = false
        luMotor.inverted = false
        ldMotor.inverted = false

        drive.isSafetyEnabled = true

        setBrakeMode()
    }

    fun resetGyro() {
        gyro.reset()
    }

    override fun periodic() {
        diffDriveOdometry.update(Rotation2d.fromDegrees(gyro.yaw.toDouble()), leftEncoder.position, rightEncoder.position)

        val currentVelocity = velocity
        if (Math.abs(currentVelocity) > 1.54) {
            shifterStatus = Consts.DriveTrain.HIGH_GEAR
            activeConversionRatio = Consts.DriveTrain.DISTANCE_PER_PULSE_HIGH
        }
        if (Math.abs(currentVelocity) < 1.54) {
            shifterStatus = Consts.DriveTrain.LOW_GEAR
            activeConversionRatio = Consts.DriveTrain.DISTANCE_PER_PULSE_LOW
        }
        SmartDashboard.putNumber("Position: ", position)
        SmartDashboard.putNumber("Speed ", currentVelocity)
        SmartDashboard.putNumber("Right Speed", rightEncoderSpeed)
        SmartDashboard.putNumber("Left Speed", leftEncoderSpeed)
        SmartDashboard.putNumber("Angle", gyro.yaw.toDouble())
    }

    fun curvatureDrive(xSpeed: Double, ySpeed: Double, quickTurn: Boolean) {
        drive.curvatureDrive(xSpeed, ySpeed, quickTurn)
    }

    fun curvatureDrive(xSpeed: Double, ySpeed: Double) {
        if (xSpeed < 0.05) {
            curvatureDrive(xSpeed, -ySpeed * 0.7, true)
        } else {
            curvatureDrive(xSpeed, -ySpeed * 0.65, false)
        }
    }



    fun setCoastMode() {
        rmMotor.idleMode = CANSparkMax.IdleMode.kCoast
        lmMotor.idleMode = CANSparkMax.IdleMode.kCoast
        ruMotor.idleMode = CANSparkMax.IdleMode.kCoast
        luMotor.idleMode = CANSparkMax.IdleMode.kCoast

    }

    fun tankDriveVolts(leftVolts: Double, rightVolts: Double) {
        SmartDashboard.putNumber("leftVolts", leftVolts)
        lmMotor.setVoltage(leftVolts)
        rmMotor.setVoltage(-rightVolts)
    }
}