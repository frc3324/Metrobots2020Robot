package frc.team3324.robot.util

import edu.wpi.first.wpilibj.Solenoid
import frc.team3324.robot.util.Consts.LED

object LED {
    private val redLED = Solenoid(LED.LED_PCM_MODULE, LED.RED_PORT)
    private val blueLED = Solenoid(LED.LED_PCM_MODULE, LED.BLUE_PORT)
    private val greenLED = Solenoid(LED.LED_PCM_MODULE, LED.GREEN_PORT)

    var redStatus: Boolean
        get() = redLED.get()
        set(bool) = redLED.set(bool)

    var blueStatus: Boolean
        get() = blueLED.get()
        set(bool) = blueLED.set(bool)

    var greenStatus: Boolean
        get() = greenLED.get()
        set(bool) = greenLED.set(bool)


}