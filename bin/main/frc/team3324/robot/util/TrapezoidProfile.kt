/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3324.robot.util

import java.util.Objects

/**
 * A trapezoid-shaped velocity profile.
 *
 *
 * While this class can be used for a profiled movement from start to finish,
 * the intended usage is to filter a reference's dynamics based on trapezoidal
 * velocity constraints. To compute the reference obeying this constraint, do
 * the following.
 *
 *
 * Initialization:
 * <pre>`
 * TrapezoidProfile.Constraints constraints =
 * new TrapezoidProfile.Constraints(kMaxV, kMaxA);
 * TrapezoidProfile.State previousProfiledReference =
 * new TrapezoidProfile.State(initialReference, 0.0);
`</pre> *
 *
 *
 * Run on update:
 * <pre>`
 * TrapezoidProfile profile =
 * new TrapezoidProfile(constraints, unprofiledReference, previousProfiledReference);
 * previousProfiledReference = profile.calculate(timeSincePreviousUpdate);
`</pre> *
 *
 *
 * where `unprofiledReference` is free to change between calls. Note that when
 * the unprofiled reference is within the constraints, `calculate()` returns the
 * unprofiled reference unchanged.
 *
 *
 * Otherwise, a timer can be started to provide monotonic values for
 * `calculate()` and to determine when the profile has completed via
 * `isFinished()`.
 */
class TrapezoidProfile
/**
 * Construct a TrapezoidProfile.
 *
 * @param constraints The constraints on the profile, like maximum velocity.
 * @param goal        The desired state when the profile is complete.
 * @param initial     The initial state (usually the current state).
 */
@JvmOverloads constructor(private val m_constraints: Constraints, goal: State, initial: State = State(0.0, 0.0)) {
    // The direction of the profile, either 1 for forwards or -1 for inverted
    private val m_direction: Int
    private val m_initial: State
    private val m_goal: State

    private val m_endAccel: Double
    private val m_endFullSpeed: Double
    private val m_endDeccel: Double

    class Constraints {
        var maxVelocity: Double = 0.toDouble()
        var maxAcceleration: Double = 0.toDouble()

        constructor() {}

        constructor(maxVelocity: Double, maxAcceleration: Double) {
            this.maxVelocity = maxVelocity
            this.maxAcceleration = maxAcceleration
        }
    }

    class State {
        var position: Double = 0.toDouble()
        var velocity: Double = 0.toDouble()

        constructor() {}

        constructor(position: Double, velocity: Double) {
            this.position = position
            this.velocity = velocity
        }

        override fun equals(other: Any?): Boolean {
            if (other is State) {
                val rhs = other as State?
                return this.position == rhs!!.position && this.velocity == rhs.velocity
            } else {
                return false
            }
        }

        override fun hashCode(): Int {
            return Objects.hash(position, velocity)
        }
    }

    init {
        m_direction = if (shouldFlipAcceleration(initial, goal)) -1 else 1
        m_initial = direct(initial)
        m_goal = direct(goal)

        if (m_initial.velocity > m_constraints.maxVelocity) {
            m_initial.velocity = m_constraints.maxVelocity
        }

        // Deal with a possibly truncated motion profile (with nonzero initial or
        // final velocity) by calculating the parameters as if the profile began and
        // ended at zero velocity
        val cutoffBegin = m_initial.velocity / m_constraints.maxAcceleration
        val cutoffDistBegin = cutoffBegin * cutoffBegin * m_constraints.maxAcceleration / 2.0

        val cutoffEnd = m_goal.velocity / m_constraints.maxAcceleration
        val cutoffDistEnd = cutoffEnd * cutoffEnd * m_constraints.maxAcceleration / 2.0

        // Now we can calculate the parameters as if it was a full trapezoid instead
        // of a truncated one

        val fullTrapezoidDist = (cutoffDistBegin + (m_goal.position - m_initial.position)
                + cutoffDistEnd)
        var accelerationTime = m_constraints.maxVelocity / m_constraints.maxAcceleration

        var fullSpeedDist = fullTrapezoidDist - (accelerationTime * accelerationTime
                * m_constraints.maxAcceleration)

        // Handle the case where the profile never reaches full speed
        if (fullSpeedDist < 0) {
            accelerationTime = Math.sqrt(fullTrapezoidDist / m_constraints.maxAcceleration)
            fullSpeedDist = 0.0
        }

        m_endAccel = accelerationTime - cutoffBegin
        m_endFullSpeed = m_endAccel + fullSpeedDist / m_constraints.maxVelocity
        m_endDeccel = m_endFullSpeed + accelerationTime - cutoffEnd
    }

    /**
     * Calculate the correct position and velocity for the profile at a time t
     * where the beginning of the profile was at time t = 0.
     *
     * @param t The time since the beginning of the profile.
     */
    fun calculate(t: Double): State {
        var result = m_initial

        if (t < m_endAccel) {
            result.velocity += t * m_constraints.maxAcceleration
            result.position += (m_initial.velocity + t * m_constraints.maxAcceleration / 2.0) * t
        } else if (t < m_endFullSpeed) {
            result.velocity = m_constraints.maxVelocity
            result.position += (m_initial.velocity + m_endAccel * m_constraints.maxAcceleration / 2.0) * m_endAccel + m_constraints.maxVelocity * (t - m_endAccel)
        } else if (t <= m_endDeccel) {
            result.velocity = m_goal.velocity + (m_endDeccel - t) * m_constraints.maxAcceleration
            val timeLeft = m_endDeccel - t
            result.position = m_goal.position - (m_goal.velocity + timeLeft * m_constraints.maxAcceleration / 2.0) * timeLeft
        } else {
            result = m_goal
        }

        return direct(result)
    }

    /**
     * Returns the time left until a target distance in the profile is reached.
     *
     * @param target The target distance.
     */
    fun timeLeftUntil(target: Double): Double {
        val position = m_initial.position * m_direction
        var velocity = m_initial.velocity * m_direction

        var endAccel = m_endAccel * m_direction
        var endFullSpeed = m_endFullSpeed * m_direction - endAccel

        if (target < position) {
            endAccel = -endAccel
            endFullSpeed = -endFullSpeed
            velocity = -velocity
        }

        endAccel = Math.max(endAccel, 0.0)
        endFullSpeed = Math.max(endFullSpeed, 0.0)
        var endDeccel = m_endDeccel - endAccel - endFullSpeed
        endDeccel = Math.max(endDeccel, 0.0)

        val acceleration = m_constraints.maxAcceleration
        val decceleration = -m_constraints.maxAcceleration

        val distToTarget = Math.abs(target - position)
        if (distToTarget < 1e-6) {
            return 0.0
        }

        var accelDist = velocity * endAccel + 0.5 * acceleration * endAccel * endAccel

        val deccelVelocity: Double
        if (endAccel > 0) {
            deccelVelocity = Math.sqrt(Math.abs(velocity * velocity + 2.0 * acceleration * accelDist))
        } else {
            deccelVelocity = velocity
        }

        var deccelDist = deccelVelocity * endDeccel + 0.5 * decceleration * endDeccel * endDeccel

        deccelDist = Math.max(deccelDist, 0.0)

        var fullSpeedDist = m_constraints.maxVelocity * endFullSpeed

        if (accelDist > distToTarget) {
            accelDist = distToTarget
            fullSpeedDist = 0.0
            deccelDist = 0.0
        } else if (accelDist + fullSpeedDist > distToTarget) {
            fullSpeedDist = distToTarget - accelDist
            deccelDist = 0.0
        } else {
            deccelDist = distToTarget - fullSpeedDist - accelDist
        }

        val accelTime = (-velocity + Math.sqrt(Math.abs(velocity * velocity + (2.0 * acceleration
                * accelDist)))) / acceleration

        val deccelTime = (-deccelVelocity + Math.sqrt(Math.abs(deccelVelocity * deccelVelocity + 2.0 * decceleration * deccelDist))) / decceleration

        val fullSpeedTime = fullSpeedDist / m_constraints.maxVelocity

        return accelTime + fullSpeedTime + deccelTime
    }

    /**
     * Returns the total time the profile takes to reach the goal.
     */
    fun totalTime(): Double {
        return m_endDeccel
    }

    /**
     * Returns true if the profile has reached the goal.
     *
     *
     * The profile has reached the goal if the time since the profile started
     * has exceeded the profile's total time.
     *
     * @param t The time since the beginning of the profile.
     */
    fun isFinished(t: Double): Boolean {
        return t >= totalTime()
    }

    /**
     * Returns true if the profile inverted.
     *
     *
     * The profile is inverted if goal position is less than the initial position.
     *
     * @param initial     The initial state (usually the current state).
     * @param goal        The desired state when the profile is complete.
     */
    private fun shouldFlipAcceleration(initial: State, goal: State): Boolean {
        return initial.position > goal.position
    }

    // Flip the sign of the velocity and position if the profile is inverted
    private fun direct(`in`: State): State {
        val result = State(`in`.position, `in`.velocity)
        result.position = result.position * m_direction
        result.velocity = result.velocity * m_direction
        return result
    }
}
/**
 * Construct a TrapezoidProfile.
 *
 * @param constraints The constraints on the profile, like maximum velocity.
 * @param goal        The desired state when the profile is complete.
 */