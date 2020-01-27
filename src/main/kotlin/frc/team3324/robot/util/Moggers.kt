package frc.team3324.robot.util

import io.github.oblarg.oblog.annotations.Log
import io.github.oblarg.oblog.annotations.Config
import edu.wpi.first.wpilibj.livewindow.LiveWindow
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab

class Moggers {
    public fun addToLog(data:Double, tab:String, valueTitle:String){
        Shuffleboard.getTab(tab)
                .add(valueTitle,data)
    }
}
