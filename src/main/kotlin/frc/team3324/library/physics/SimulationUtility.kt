package frc.team3324.robot.util.physics

import java.io.File

object SimulationUtility {
    fun clampVoltage(voltage: Double): Double {
        return voltage.coerceIn(-11.0, 11.0)
    }

    fun plot(values: List<Double>, times: List<Double>, fileName: String, dataName: String) {
        File(fileName).printWriter().use { out ->
            out.println(values.joinToString(","))
            out.println(times.joinToString(","))}
        Runtime.getRuntime().exec("python3 ./plot.py $fileName $dataName")
    }
}