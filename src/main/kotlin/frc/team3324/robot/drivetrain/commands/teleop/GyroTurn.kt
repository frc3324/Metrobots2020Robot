package frc.team3324.robot.drivetrain.commands.teleop


import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard



private val gyro = AHRS(SPI.Port.kMXP)
private val sensorTab = Shuffleboard.getTab("Encoder Values")
private val gyroYaw: NetworkTableEntry = sensorTab.add("Gyro Yaw", 0).withPosition(0, 2).getEntry()

class GyroTurn {
    fun clearGyro() {
        gyro.reset()
    }

    object GetYaw {
        fun getYaw(): Double {
            return gyro.yaw.toDouble()
        }
    }

    fun updateSensors() {
        gyroYaw.setNumber(0)
    }
}