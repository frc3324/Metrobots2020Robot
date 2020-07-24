package frc.team3324.robot.autocommands

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.team3324.library.subsystems.MotorSubsystem
import frc.team3324.robot.drivetrain.DriveTrain
import frc.team3324.robot.drivetrain.commands.auto.RunDrivetrain
import frc.team3324.robot.drivetrain.commands.teleop.GyroTurn
import frc.team3324.robot.intake.Pivot
import frc.team3324.robot.shooter.Shooter
import frc.team3324.robot.util.Consts

class FinalAutoGroup(pivot: Pivot, driveTrain: DriveTrain, shooter: Shooter, storage: MotorSubsystem, cameraTable: NetworkTable): SequentialCommandGroup() {
    init {
        addCommands(GyroTurn(driveTrain, 1.0/80.0, Consts.DriveTrain.ksVolts/12, {cameraTable.getEntry("targetYaw").getDouble(0.0)}, {input -> driveTrain.curvatureDrive(0.0, input, true)}).withTimeout(1.0),
                 ShooterAndStorageParallel(pivot, shooter, storage, {cameraTable.getEntry("targetArea").getDouble(0.0)}).withTimeout(10.0), RunDrivetrain(driveTrain, -6.0).withTimeout(1.0))
    }
}