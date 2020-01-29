package frc.team3324.robot.util

//import io.github.oblarg.oblog.annotations.Log
//import io.github.oblarg.oblog.annotations.Config
import edu.wpi.first.wpilibj.livewindow.LiveWindow
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser

public object Moggers {
    public fun addToLog(data:Double, tab:String, valueTitle:String){
        Shuffleboard.getTab(tab)
                .add(valueTitle,data)
    }
    public fun getValue(tab:String, valueTitle: String): Double {
        return Shuffleboard.getTab(tab).add(valueTitle, 0.0).entry.getDouble(0.0)
    }
    public fun addChooser(chooser: SendableChooser<Double>, tab: String, valueTitle: String){
        Shuffleboard.getTab(tab)
                .add(valueTitle, chooser)
    }
}
