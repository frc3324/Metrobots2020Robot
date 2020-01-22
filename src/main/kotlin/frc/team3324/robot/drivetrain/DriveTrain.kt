package frc.team3324.robot.drivetrain

import com.kauailabs.navx.frc.AHRS
import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.CounterBase
import edu.wpi.first.wpilibj.DoubleSolenoid

import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team3324.robot.drivetrain.commands.teleop.Drive

import frc.team3324.robot.util.Consts

class DriveTrain: SubsystemBase() {

    private val lEncoder = Encoder(Consts.DriveTrain.LEFT_ENCODER_PORT_A, Consts.DriveTrain.LEFT_ENCODER_PORT_B, false, CounterBase.EncodingType.k4X)
    private val rEncoder = Encoder(Consts.DriveTrain.RIGHT_ENCODER_PORT_A, Consts.DriveTrain.RIGHT_ENCODER_PORT_B, true, CounterBase.EncodingType.k4X)
    val driveKinematics = DifferentialDriveKinematics(Consts.DriveTrain.DISTANCE_BETWEEN_WHEELS)
    val gearShifter = DoubleSolenoid(Consts.DriveTrain.DRIVETRAIN_PCM_MODULE, Consts.DriveTrain.GEARSHIFTER_FORWARD, Consts.DriveTrain.GEARSHIFTER_REVERSE)
    var shifterStatus: DoubleSolenoid.Value
        get() = gearShifter.get()
        set(status) {
            gearShifter.set(status)
        }
    val accelerationGyro: Double
        get() = gyro.accelFullScaleRangeG.toDouble() * 9.8
    val leftEncoderSpeed: Double
        get() = lEncoder.rate
    val rightEncoderSpeed: Double
        get() = rEncoder.rate
    val pose: Pose2d
        get() = diffDriveOdometry.poseMeters
    val wheelSpeeds: DifferentialDriveWheelSpeeds
        get() = DifferentialDriveWheelSpeeds(leftEncoderSpeed, rightEncoderSpeed)

    var speed = 0.0
    var acceleration = 0.0


    private val gyro = AHRS(SPI.Port.kMXP)

    private val flMotor = CANSparkMax(Consts.DriveTrain.FL_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val blMotor = CANSparkMax(Consts.DriveTrain.BL_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless)

    private val frMotor = CANSparkMax(Consts.DriveTrain.FR_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val brMotor = CANSparkMax(Consts.DriveTrain.BR_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val rightEncoder = frMotor.encoder
    private val leftEncoder = flMotor.encoder
    var safety: Boolean
        get() = drive.isSafetyEnabled
        set(bool) = drive.setSafetyEnabled(bool)

    private val drive = DifferentialDrive(frMotor, flMotor)

    val diffDriveOdometry = DifferentialDriveOdometry(Rotation2d.fromDegrees(gyro.yaw.toDouble()))
    init {
        frMotor.restoreFactoryDefaults()
        flMotor.restoreFactoryDefaults()
        brMotor.restoreFactoryDefaults()
        blMotor.restoreFactoryDefaults()
        rightEncoder.positionConversionFactor = Consts.DriveTrain.CIRCUMFERENCE / Consts.DriveTrain.HIGH_GEAR_RATIO
        leftEncoder.velocityConversionFactor = Consts.DriveTrain.CIRCUMFERENCE / Consts.DriveTrain.HIGH_GEAR_RATIO

        frMotor.setSmartCurrentLimit(40)
        flMotor.setSmartCurrentLimit(40)
        frMotor.setSecondaryCurrentLimit(80.0)
        flMotor.setSecondaryCurrentLimit(80.0)
        frMotor.openLoopRampRate = 0.01
        flMotor.openLoopRampRate = 0.01

        brMotor.follow(frMotor)
        blMotor.follow(flMotor)

        brMotor.inverted = false
        frMotor.inverted= true

        flMotor.inverted = true
        blMotor.inverted = true

        drive.isSafetyEnabled = true

        setBrakeMode()
    }

    fun getLeftEncoderDistance(): Double {
        return leftEncoder.position
    }

    fun getRightEncoderDistance(): Double {
        return rightEncoder.position
    }

    fun getAverageDistance(): Double {
        return (lEncoder.distance + rEncoder.distance) / 2.0
    }

    fun resetGyro() {
        gyro.reset()
    }

    fun getYaw(): Double {
        return gyro.yaw.toDouble()
    }

    override fun periodic() {
        diffDriveOdometry.update(Rotation2d.fromDegrees(gyro.yaw.toDouble()), leftEncoder.position, rightEncoder.position)
        var currentAngle = gyro.angle
        var setPoint = 0
        var error = setPoint - currentAngle
        var kP = 1/90
        var speed = error * kP
        curvatureDrive(0.0,ySpeed = speed, quickTurn = true)
    }

    fun curvatureDrive(xSpeed: Double, ySpeed: Double, quickTurn: Boolean) {
        drive.curvatureDrive(xSpeed, ySpeed, quickTurn)
    }

    fun curvatureDrive(xSpeed: Double, ySpeed: Double) {
        if (xSpeed < 0.05) {
            drive.curvatureDrive(xSpeed, ySpeed * 0.7, true)
        } else {
            drive.curvatureDrive(xSpeed, ySpeed * 0.65, false)
        }
    }

    fun setBrakeMode() {
        frMotor.idleMode = CANSparkMax.IdleMode.kBrake
        flMotor.idleMode = CANSparkMax.IdleMode.kBrake
        brMotor.idleMode = CANSparkMax.IdleMode.kBrake
        blMotor.idleMode = CANSparkMax.IdleMode.kBrake
    }

    fun setCoastMode() {
        frMotor.idleMode = CANSparkMax.IdleMode.kCoast
        flMotor.idleMode = CANSparkMax.IdleMode.kCoast
        brMotor.idleMode = CANSparkMax.IdleMode.kCoast
        blMotor.idleMode = CANSparkMax.IdleMode.kCoast

    }

    fun tankDriveVolts(leftVolts: Double, rightVolts: Double) {
        SmartDashboard.putNumber("leftVolts", leftVolts)
        flMotor.setVoltage(leftVolts)
        frMotor.setVoltage(-rightVolts)
    }

}