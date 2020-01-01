import frc.team3324.robot.arm.Arm
import frc.team3324.robot.util.physics.ArmSimulator
import frc.team3324.robot.util.physics.Motors
import org.junit.jupiter.api.Test

class Main {
    @Test
    fun test() {
        testPIDSim(Arm.motor, 147.0, 0.7, 0.0, 0.0, Math.toRadians(90.0), 0.0, 0.0, 1.0, 0.72517)
    }

    fun testPIDSim(motor: Motors.Motor, gearRatio: Double, Kp: Double, Ki: Double, Kd: Double, goal: Double, startingPosition: Double, startingVelocity: Double, timeLimit: Double, momentOfInertia: Double) {
        val sim = ArmSimulator(5.0, motor, gearRatio, momentOfInertia)
        val pid: (Double, Double) -> Double = {position: Double, _: Double -> (goal - position) * Kp * 12}
        val position = sim.simulatePosition(startingPosition, startingVelocity, pid)
        val error = goal - position.last()
        println("Error = $error")
        assert(error < (goal * 0.01))
    }
}