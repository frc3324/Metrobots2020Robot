package frc.team3324.robot.util

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard

class PredictiveCurrentLimiting(val motor: Motors.Motor, val upperLimit: Double, val lowerLimit: Double) {

    fun limit(voltage: Double, angularVelocity: Double): Double {
        var voltage = voltage
        val motorVel = angularVelocity * Consts.Arm.GEAR_RATIO
        val voltageMin = lowerLimit * motor.resistance + motorVel/motor.Kv
        val voltageMax = upperLimit * motor.resistance + motorVel/motor.Kv
        voltage = Math.max(voltageMin, voltage)
        voltage = Math.min(voltageMax, voltage)
        SmartDashboard.putNumber("Kv", motor.Kv)
        SmartDashboard.putNumber("Voltage min", voltageMin)
        SmartDashboard.putNumber("Voltage max", voltageMax)
        SmartDashboard.putNumber("Voltage", voltage)
        SmartDashboard.putNumber("BackEMF constant", (motorVel/motor.Kv))
        SmartDashboard.putNumber("rad", motorVel)
        return voltage
    }
}