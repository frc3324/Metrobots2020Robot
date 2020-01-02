package frc.team3324.robot.arm

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
import edu.wpi.first.wpilibj.CounterBase
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.command.Subsystem
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team3324.robot.arm.commands.ControlArm
import frc.team3324.robot.util.Consts
import frc.team3324.robot.util.physics.Motors
import frc.team3324.robot.util.physics.PredictiveCurrentLimiting
import frc.team3324.robot.Robot

object Arm: Subsystem() {
    private val encoder = Encoder(Consts.Arm.ENCODER_PORT_A, Consts.Arm.ENCODER_PORT_B, true, CounterBase.EncodingType.k4X)
    private val frontSwitch = DigitalInput(Consts.Arm.FRONT_LIMIT_SWITCH)
    private val backSwitch = DigitalInput(Consts.Arm.BACK_LIMIT_SWITCH)
    val motor = Motors.MiniCim(3.0)
    private val currentLimiter = PredictiveCurrentLimiting(motor, 8.0, -8.0)

    val frontSwitchStatus get() = frontSwitch.get()
    val backSwitchStatus get() = backSwitch.get()

    private val armMotorOne = WPI_TalonSRX(Consts.Arm.MOTOR_PORT_ARM_ONE)
    private val armMotorTwo = WPI_VictorSPX(Consts.Arm.MOTOR_PORT_ARM_TWO)
    private val armMotorThree  = WPI_VictorSPX(Consts.Arm.MOTOR_PORT_ARM_THREE)

    var acceleration = 0.0
    val velocity: Double
        get() = encoder.rate


    init {
        encoder.distancePerPulse = Consts.Arm.DISTANCE_PER_PULSE
        initializeCurrentLimiting()
        setBrakeMode()
    }

    private fun initializeCurrentLimiting() {
        armMotorOne.configContinuousCurrentLimit(12)
        armMotorOne.enableCurrentLimit(false)

        armMotorTwo.follow(armMotorOne)
        armMotorThree.follow(armMotorOne)
    }

    fun setBrakeMode() {
        armMotorOne.setNeutralMode(NeutralMode.Brake)
        armMotorTwo.setNeutralMode(NeutralMode.Brake)
        armMotorThree.setNeutralMode(NeutralMode.Brake)
    }


    fun setSpeed(speed: Double) {
        var speed = speed
        if (frontSwitch.get()) { resetEncoder() }
        if (armIsAtHardstop(speed)) {
            speed = 0.0
        }
        val feedforward = 0.06 * Math.cos(getArmRadians())
        speed += feedforward
//        val pdpVoltage = Robot.pdp.voltage
//        SmartDashboard.putNumber("Non-limited Voltage", speed * pdpVoltage)
//        val voltage = currentLimiter.limit(speed * pdpVoltage, velocity)
//        speed = voltage / pdpVoltage
        SmartDashboard.putNumber("Arm Position", encoder.distance)
        SmartDashboard.putNumber("Arm current", armMotorOne.outputCurrent * 3)
        armMotorOne.set(speed)
    }

    fun setArmRawSpeed(speed: Double) {
        armMotorOne.set(speed)
    }

    private fun armIsAtHardstop(speed: Double): Boolean {
        return (encoder.get() <= 0 && speed < 0) || (encoder.get() >= (Consts.Arm.ENCODER_TICKS_PER_REV) / 2 && speed > 0) ||
                ((frontSwitch.get() && speed < 0) || (backSwitch.get() && speed > 0))
    }

    fun getArmRadians(): Double {
        return encoder.get() * ((Math.PI * 2) / Consts.Arm.ENCODER_TICKS_PER_REV)
    }

    private fun resetEncoder() {
        encoder.reset()
    }

    override fun initDefaultCommand() {
        defaultCommand = ControlArm()
    }
}