package frc.team3324.robot.autocommands

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.team3324.robot.intake.Pivot
import frc.team3324.robot.intake.commands.RunPivot
import frc.team3324.robot.shooter.Shooter
import frc.team3324.robot.shooter.commands.RunShooter
import frc.team3324.robot.storage.Storage
import frc.team3324.robot.storage.commands.RunStorageConstant

class ShooterAndStorageParallel(pivot: Pivot, shooter: Shooter, storage: Storage, area: () -> Double): ParallelCommandGroup() {

    init {
        addCommands(RunShooter(shooter, area, true), WaitCommand(2.0).andThen(RunStorageConstant(storage, 0.4)), RunPivot(pivot, 0.5))
    }
}