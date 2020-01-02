package frc.team3324.robot.util.physics

import kotlin.math.PI

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
    class Cim(numberOfMotors: Int): Motor() {
        override val freeSpeed = rpmToRad(5310.0)
        override val freeCurrent = 2.7
        override val stallTorque = 2.42 * numberOfMotors
        override val stallCurrent = 133.0

        override val Kt = stallTorque / stallCurrent
        override val resistance = 12 / stallCurrent
        override val Kv = freeSpeed / (12 - freeCurrent * resistance)
    }

    class MiniCim(numberOfMotors: Int): Motor() {
        override val freeSpeed = rpmToRad(6200.0)
        override val freeCurrent = 1.5
        override val stallTorque = 1.4 * numberOfMotors
        override val stallCurrent = 86.0 / numberOfMotors

        override val Kt = stallTorque / stallCurrent
        override val resistance = 12 / stallCurrent
        override val Kv = freeSpeed / (12 - (freeCurrent * resistance))
    }
    fun rpmToRad(rpm: Double) : Double {
        return rpm * (PI/30)
    }
}