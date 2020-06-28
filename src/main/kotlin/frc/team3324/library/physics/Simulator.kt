package frc.team3324.robot.util.physics

import frc.team3324.robot.util.physics.SimulationUtility.clampVoltage
import java.io.File
import kotlin.math.pow

class ArmSimulator(val timeLimit: Double, val motor: Motors.Motor, val gearRatio: Double, val momentOfInertia: Double) {
    val timeStep = 0.0001

    fun calculateAcceleration(voltage: Double, velocity: Double): Double {
        return (gearRatio * motor.Kt / (motor.resistance * momentOfInertia)) * voltage - (gearRatio.pow(2) * motor.Kt) / (motor.Kv * motor.resistance * momentOfInertia) * velocity
    }

    fun simulatePosition(startingPosition: Double, startingVelocity: Double, useOutput: (Double, Double) -> Double): List<Double> {
        var time = 0.0
        var position = startingPosition
        var velocity = startingVelocity
        var acceleration = calculateAcceleration(useOutput(position, velocity), velocity)
        val positionList = mutableListOf(position)
        val timeList = mutableListOf(time)
        while (time < timeLimit) {
            position += velocity * timeStep
            velocity += acceleration * timeStep
            acceleration = calculateAcceleration(clampVoltage(useOutput(position, velocity)), velocity)
            time += timeStep
            positionList.add(position)
            timeList.add(time)
        }
        SimulationUtility.plot(positionList, timeList, "./armPositions.csv", "Position")
        return positionList
    }

    fun simulateVelocity(startingVelocity: Double, useOutput: (Double) -> Double): List<Double> {
        var time = 0.0
        var velocity = startingVelocity
        var acceleration = calculateAcceleration(useOutput(velocity), velocity)
        val velocityList = mutableListOf(velocity)
        while (time < timeLimit) {
            velocity += acceleration * timeStep
            acceleration = calculateAcceleration(useOutput(velocity), velocity)
            time += timeStep
            velocityList.add(velocity)
        }
        return velocityList
    }
    fun simulatePosition(useOutput: (Double, Double) -> Double): List<Double> {
        return simulatePosition(0.0, 0.0, useOutput)
    }

    fun simulateVelocity(useOutput: (Double) -> Double): List<Double> {
        return simulateVelocity(0.0, useOutput)
    }


}
