package frc.team3324.robot.util

import edu.wpi.first.wpilibj.DoubleSolenoid

object Consts {
    object DriveTrain {
        const val DRIVETRAIN_PCM_MODULE = 0

        // Motor ports
        const val FL_MOTOR = 4
        const val BL_MOTOR = 3
        const val FR_MOTOR = 1
        const val BR_MOTOR = 2

        const val LEFT_ENCODER_PORT_A = 0
        const val LEFT_ENCODER_PORT_B = 1
        const val RIGHT_ENCODER_PORT_A = 2
        const val RIGHT_ENCODER_PORT_B = 3

        // Encoder and Auto constants
        const val WHEEL_DIAMETER_METERS = 0.1555575
        const val CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER_METERS
        const val PULSES = 1870 // 256 (pulses) * 4(quad, 4 ticks/pulse) * 3 * 25 (gear ratios)
        const val TICKS = PULSES * 4
        const val DISTANCE_PER_PULSE = CIRCUMFERENCE / PULSES
        const val DISTANCE_BETWEEN_WHEELS = 0.61


        const val HIGH_GEAR_RATIO = 1/9.167
        const val LOW_GEAR_RATIO = 1/20.833
        const val HIGH_GEAR_MAX_VELOCITY = 3.0435
        const val HIGH_GEAR_MAX_ACCELERATION = 2.00
        const val LOW_GEAR_MAX_VELOCITY = 1.8
        const val LOW_GEAR_MAX_ACCELERATION = 6.51

        const val ksVolts = 0.181
        const val kvVoltSecondsPerMeter = 2.3
        const val kaVoltSecondsSquaredPerMeter = 0.302
        const val kPDriveVel = 5
        const val kRamseteB = 2.0
        const val kRamseteZeta = 0.7

        const val GEARSHIFTER_FORWARD = 0
        const val GEARSHIFTER_REVERSE = 1

        val HIGH_GEAR = DoubleSolenoid.Value.kReverse
        val LOW_GEAR = DoubleSolenoid.Value.kForward

    }

    object LED {
        const val LED_PCM_MODULE = 1

        const val RED_PORT = 7
        const val GREEN_PORT = 6
        const val BLUE_PORT = 5
    }

    object Shooter {
        const val LEFT_MOTOR_PORT = 10
        const val RIGHT_MOTOR_PORT = 9
        const val GEAR_RATIO = 1.25
    }

}