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
import kotlin.math.sign

class DriveTrain: SubsystemBase() {

    val driveKinematics = DifferentialDriveKinematics(Consts.DriveTrain.DISTANCE_BETWEEN_WHEELS)
    val gearShifter = DoubleSolenoid(Consts.DriveTrain.GEARSHIFTER_FORWARD, Consts.DriveTrain.GEARSHIFTER_REVERSE)
    var shifterStatus: DoubleSolenoid.Value
        get() = gearShifter.get()
        set(status) {
            gearShifter.set(status)
        }
    val accelerationGyro: Double
        get() = gyro.accelFullScaleRangeG.toDouble() * 9.8
    val leftEncoderSpeed: Double
        get() = leftEncoder.velocity
    val leftEncoderPosition: Double
        get() = leftEncoder.position
    val rightEncoderSpeed: Double
        get() = rightEncoder.velocity
    val pose: Pose2d
        get() = diffDriveOdometry.poseMeters
    val wheelSpeeds: DifferentialDriveWheelSpeeds
        get() = DifferentialDriveWheelSpeeds(leftEncoderSpeed, rightEncoderSpeed)

    var speed = 0.0
    var acceleration = 0.0


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

    fun setBrakeMode() {
        rmMotor.idleMode = CANSparkMax.IdleMode.kBrake
        ruMotor.idleMode = CANSparkMax.IdleMode.kBrake
        rdMotor.idleMode = CANSparkMax.IdleMode.kBrake
        lmMotor.idleMode = CANSparkMax.IdleMode.kBrake
        luMotor.idleMode = CANSparkMax.IdleMode.kBrake
        ldMotor.idleMode = CANSparkMax.IdleMode.kBrake
    }

    init {
        lmMotor.restoreFactoryDefaults()
        luMotor.restoreFactoryDefaults()
        ldMotor.restoreFactoryDefaults()

        rmMotor.restoreFactoryDefaults()
        ruMotor.restoreFactoryDefaults()
        rdMotor.restoreFactoryDefaults()

        rightEncoder.positionConversionFactor = Consts.DriveTrain.CIRCUMFERENCE / Consts.DriveTrain.HIGH_GEAR_RATIO //rotations to meters
        leftEncoder.positionConversionFactor = Consts.DriveTrain.CIRCUMFERENCE / Consts.DriveTrain.HIGH_GEAR_RATIO //rotations to meters
        rightEncoder.velocityConversionFactor = (Consts.DriveTrain.CIRCUMFERENCE / Consts.DriveTrain.HIGH_GEAR_RATIO)/60 //rpm to mps
        leftEncoder.velocityConversionFactor = (Consts.DriveTrain.CIRCUMFERENCE / Consts.DriveTrain.HIGH_GEAR_RATIO)/60 //rpm to mps

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
        ldMotor.inverted = true

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
        return (leftEncoder.position + rightEncoder.position) / 2.0
    }

    fun resetGyro() {
        gyro.reset()
    }

    fun getYaw(): Double {
        return gyro.yaw.toDouble()
    }

    override fun periodic() {
        diffDriveOdometry.update(Rotation2d.fromDegrees(gyro.yaw.toDouble()), leftEncoder.position, rightEncoder.position)

        var lastTime = 0.0
        var lastPosition = 0.0
        var lastSpeed = 0.0
        var lastShift = 0.0
        var enabled = true


        var timeDifference = Timer.getFPGATimestamp() - lastTime

        val timeBetweenShifts = Timer.getFPGATimestamp() - lastShift
        lastTime = Timer.getFPGATimestamp()

        if (timeDifference > 0.5) {
            timeDifference = 0.2
        }

        if (sign(getLeftEncoderDistance()) * sign(getRightEncoderDistance()) != -1.0 && enabled) {
            if (shifterStatus == Consts.DriveTrain.LOW_GEAR && speed > (Consts.DriveTrain.LOW_GEAR_MAX_VELOCITY * 0.7 ) && timeBetweenShifts > 0.5) {
                shifterStatus = Consts.DriveTrain.HIGH_GEAR
                lastShift = Timer.getFPGATimestamp()

                rightEncoder.positionConversionFactor = Consts.DriveTrain.CIRCUMFERENCE / Consts.DriveTrain.HIGH_GEAR_RATIO
                leftEncoder.positionConversionFactor = Consts.DriveTrain.CIRCUMFERENCE / Consts.DriveTrain.HIGH_GEAR_RATIO
                rightEncoder.velocityConversionFactor = (Consts.DriveTrain.CIRCUMFERENCE / Consts.DriveTrain.HIGH_GEAR_RATIO) / 60
                leftEncoder.velocityConversionFactor = (Consts.DriveTrain.CIRCUMFERENCE / Consts.DriveTrain.HIGH_GEAR_RATIO) / 60

            } else if (shifterStatus == Consts.DriveTrain.HIGH_GEAR && speed < (Consts.DriveTrain.LOW_GEAR_MAX_VELOCITY * 0.5) && timeBetweenShifts > 0.5) {
                lastShift = Timer.getFPGATimestamp()
                shifterStatus = Consts.DriveTrain.LOW_GEAR
                
                rightEncoder.positionConversionFactor = Consts.DriveTrain.CIRCUMFERENCE / Consts.DriveTrain.LOW_GEAR_RATIO
                leftEncoder.positionConversionFactor = Consts.DriveTrain.CIRCUMFERENCE / Consts.DriveTrain.LOW_GEAR_RATIO
                rightEncoder.velocityConversionFactor = (Consts.DriveTrain.CIRCUMFERENCE / Consts.DriveTrain.LOW_GEAR_RATIO) / 60
                leftEncoder.velocityConversionFactor = (Consts.DriveTrain.CIRCUMFERENCE / Consts.DriveTrain.LOW_GEAR_RATIO) / 60
            }
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
}