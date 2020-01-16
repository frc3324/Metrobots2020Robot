package frc.team3324.robot.util.physics

import edu.wpi.first.wpilibj.util.Units
import edu.wpi.first.wpilibj.util.Units.rotationsPerMinuteToRadiansPerSecond

object Motors {
    abstract class Motor {
        abstract val freeSpeed: Double
        abstract val freeCurrent: Double
        abstract val stallTorque: Double
        abstract val stallCurrent: Double

        abstract val Kt: Double
        abstract val resistance: Double
        abstract val Kv: Double

    }

    class Neo(numberOfMotors: Int): Motor() {
        override val freeSpeed = rotationsPerMinuteToRadiansPerSecond(5880.0)
        override val freeCurrent = 1.3
        override val stallTorque = 3.36 * numberOfMotors
        override val stallCurrent = 166.0

        override val Kt = stallTorque / stallCurrent
        override val resistance = 12 / stallCurrent
        override val Kv = freeSpeed / (12 - freeCurrent * resistance)
    }

    class Cim(numberOfMotors: Int): Motor() {
        override val freeSpeed = rotationsPerMinuteToRadiansPerSecond(5310.0)
        override val freeCurrent = 2.7
        override val stallTorque = 2.42 * numberOfMotors
        override val stallCurrent = 133.0

        override val Kt = stallTorque / stallCurrent
        override val resistance = 12 / stallCurrent
        override val Kv = freeSpeed / (12 - freeCurrent * resistance)
    }

    class MiniCim(numberOfMotors: Int): Motor() {
        override val freeSpeed = rotationsPerMinuteToRadiansPerSecond(6200.0)
        override val freeCurrent = 1.5
        override val stallTorque = 1.4 * numberOfMotors
        override val stallCurrent = 86.0 / numberOfMotors

        override val Kt = stallTorque / stallCurrent
        override val resistance = 12 / stallCurrent
        override val Kv = freeSpeed / (12 - (freeCurrent * resistance))
    }

    fun getPercentFromRPM(desiredRPM: Double, motor: Motor): Double {
        return desiredRPM/ Units.radiansPerSecondToRotationsPerMinute(motor.freeSpeed)
    }

}