package frc.team3324.robot.drivetrain.commands.teleop


import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj2.command.CommandBase

class GyroTurn(val kP: Double,val setPoint: Double, val input: () -> Double, val output: (Double) -> Unit):CommandBase() {
    override fun execute() {
        var currentAngle = input()
        var error = setPoint - currentAngle
        var kP = 1 / 90
        var speed = error * kP
        output(speed)
    }

    override fun isFinished(): Boolean {
        return input()==setPoint
    }
}