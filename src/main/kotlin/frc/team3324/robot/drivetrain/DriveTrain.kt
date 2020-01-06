package frc.team3324.robot.drivetrain

import com.kauailabs.navx.frc.AHRS
import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.CounterBase
import edu.wpi.first.wpilibj.DoubleSolenoid

import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.team3324.robot.drivetrain.commands.teleop.Drive

import frc.team3324.robot.util.Consts

class DriveTrain: SubsystemBase() {

    private val lEncoder = Encoder(Consts.DriveTrain.LEFT_ENCODER_PORT_A, Consts.DriveTrain.LEFT_ENCODER_PORT_B, false, CounterBase.EncodingType.k4X)
    private val rEncoder = Encoder(Consts.DriveTrain.RIGHT_ENCODER_PORT_A, Consts.DriveTrain.RIGHT_ENCODER_PORT_B, true, CounterBase.EncodingType.k4X)

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

    var speed = 0.0
    var acceleration = 0.0


    private val gyro = AHRS(SPI.Port.kMXP)

    private val flMotor = CANSparkMax(Consts.DriveTrain.FL_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val blMotor = CANSparkMax(Consts.DriveTrain.BL_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless)

    private val frMotor = CANSparkMax(Consts.DriveTrain.FR_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val brMotor = CANSparkMax(Consts.DriveTrain.BR_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless)

    private val drive = DifferentialDrive(frMotor, blMotor)

    init {

        frMotor.setSmartCurrentLimit(40)
        flMotor.setSmartCurrentLimit(40)
        frMotor.setSecondaryCurrentLimit(80.0)
        flMotor.setSecondaryCurrentLimit(80.0)

        brMotor.follow(frMotor)
        blMotor.follow(flMotor)

        brMotor.inverted = false
        frMotor.inverted= true
        
        blMotor.inverted = true
        flMotor.inverted = true

        lEncoder.distancePerPulse = Consts.DriveTrain.DISTANCE_PER_PULSE
        rEncoder.distancePerPulse = Consts.DriveTrain.DISTANCE_PER_PULSE

        setBrakeMode()
    }

    fun resetEncoders() {
        lEncoder.reset()
        rEncoder.reset()
    }

    fun getLeftEncoderDistance(): Double {
        return lEncoder.distance
    }

    fun getRightEncoderDistance(): Double {
        return rEncoder.distance
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

    fun curvatureDrive(xSpeed: Double, ySpeed: Double, quickTurn: Boolean) {
        drive.curvatureDrive(xSpeed, ySpeed, quickTurn)
    }

    fun curvatureDrive(xSpeed: Double, ySpeed: Double) {
        SmartDashboard.putNumber("Current Of FLMotor ", blMotor.outputCurrent)
        SmartDashboard.putNumber("Current Of FRMotor ", frMotor.outputCurrent)

        if (xSpeed < 0.05) {
            drive.curvatureDrive(xSpeed, ySpeed * 0.65, true)
        } else {
            drive.curvatureDrive(xSpeed, ySpeed * 0.7, false)
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

}
