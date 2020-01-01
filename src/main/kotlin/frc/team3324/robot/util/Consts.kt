package frc.team3324.robot.util

import edu.wpi.first.wpilibj.DoubleSolenoid

object Consts {
    object DriveTrain {
        const val DRIVETRAIN_PCM_MODULE = 0

        // Motor ports
        const val FL_MOTOR = 4 // Victor
        const val BL_MOTOR = 3 // Talon
        const val FR_MOTOR = 1 // Talon
        const val BR_MOTOR = 2 // Victor

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

        const val HIGH_GEAR_MAX_VELOCITY = 3.4072
        const val HIGH_GEAR_MAX_ACCELERATION = 3.00
        const val LOW_GEAR_MAX_VELOCITY = 1.8
        const val LOW_GEAR_MAX_ACCELERATION = 6.51

        const val GEARSHIFTER_FORWARD = 0
        const val GEARSHIFTER_REVERSE = 1

        val HIGH_GEAR = DoubleSolenoid.Value.kReverse
        val LOW_GEAR = DoubleSolenoid.Value.kForward

    }

    object Arm {
        const val MOTOR_PORT_ARM_ONE = 2
        const val MOTOR_PORT_ARM_TWO = 3
        const val MOTOR_PORT_ARM_THREE = 5

        const val ENCODER_PORT_A = 7
        const val ENCODER_PORT_B = 6
        const val GEAR_RATIO = 147

        const val ENCODER_TICKS_PER_REV = 256.0
        const val DISTANCE_PER_PULSE = Math.PI / (ENCODER_TICKS_PER_REV / 2)

        const val FRONT_LIMIT_SWITCH = 9
        const val BACK_LIMIT_SWITCH = 8
    }

    object HatchIntake {
        const val HATCH_INTAKE_PORT_FORWARD = 6
        const val HATCH_INTAKE_PORT_BACKWARD = 7
    }

    object CargoIntake {
        const val CARGO_INTAKE_MOTOR = 4
    }

    object Climber {
        const val BACK_PCM_MODULE = 1

        const val BACK_FORWARD = 5
        const val BACK_BACKWARD = 4
        const val FRONT_FORWARD = 1
        const val FRONT_BACKWARD = 0
    }

    object LED {
        const val LED_PCM_MODULE = 1

        const val RED_PORT = 7
        const val GREEN_PORT = 6
        const val BLUE_PORT = 5
    }

}