package frc.team3324.robot.drivetrain.commands.auto

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.team3324.robot.drivetrain.DriveTrain

class RunDrivetrain(val driveTrain: DriveTrain, val speedVolts: Double): CommandBase() {

    init {
        addRequirements(driveTrain)
    }

    override fun execute() {
        driveTrain.tankDriveVolts(speedVolts, speedVolts)
    }
}