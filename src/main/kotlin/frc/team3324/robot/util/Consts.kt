package frc.team3324.robot.util

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.util.Units

object Consts {
    object DriveTrain {
        // Motor ports
        const val LM_MOTOR = 1
        const val LU_MOTOR = 2
        const val LD_MOTOR = 3
        const val RM_MOTOR = 5
        const val RU_MOTOR = 4
        const val RD_MOTOR = 6

        // Encoder and Auto constants
        const val HIGH_GEAR_RATIO = 1.0 / (108800 / 12000)
        const val LOW_GEAR_RATIO = 1.0 / (160000 / 8160)
        const val WHEEL_DIAMETER_METERS = 6.00 / 39.36
        const val CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER_METERS
        const val PULSES = 1870 // 256 (pulses) * 4(quad, 4 ticks/pulse) * 3 * 25 (gear ratios)
        const val TICKS = PULSES * 4
        const val DISTANCE_PER_PULSE_HIGH = CIRCUMFERENCE * HIGH_GEAR_RATIO
        const val DISTANCE_PER_PULSE_LOW = CIRCUMFERENCE * LOW_GEAR_RATIO
        const val DISTANCE_BETWEEN_WHEELS = 0.61

        const val ksVolts = 0.181
        const val LOW_GEAR_KV = 4.9
        const val HIGH_GEAR_KV = 2.28
        const val LOW_GEAR_KA = 0.36
        const val kaVoltSecondsSquaredPerMeter = 0.302
        const val kRamseteB = 2.0
        const val kRamseteZeta = 0.7

        const val HIGH_GEAR_MAX_VELOCITY = 12.0 / HIGH_GEAR_KV
        const val HIGH_GEAR_MAX_ACCELERATION = 2.00
        const val LOW_GEAR_MAX_VELOCITY = 12.0 / LOW_GEAR_KV
        const val LOW_GEAR_MAX_ACCELERATION = 6.51


        const val GEARSHIFTER_FORWARD = 6
        const val GEARSHIFTER_REVERSE = 7

        val HIGH_GEAR = DoubleSolenoid.Value.kReverse
        val LOW_GEAR = DoubleSolenoid.Value.kForward

    }

    object Shooter {
        const val LEFT_MOTOR_PORT = 11
        const val RIGHT_MOTOR_PORT = 10
        const val GEAR_RATIO = 1.25

        const val Kv = 0.107 / 60 // Char tool gives Kv in terms of RPS so /60
        const val Ks = 0.101
        const val Kp = 1.06e-5
    }

    object Storage {
        const val MOTOR_TOP = 21
        const val MOTOR_BOTTOM = 7
    }

    object Climber {
        const val MOTOR_LEFT = 1
        const val MOTOR_RIGHT = 25
    }
}