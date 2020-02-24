package frc.team3324.robot.autocommands

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup
import frc.team3324.robot.shooter.Shooter
import frc.team3324.robot.shooter.commands.RunShooter
import frc.team3324.robot.storage.Storage
import frc.team3324.robot.storage.commands.RunStorageConstant

class ShooterAndStorageParallel(shooter: Shooter, storage: Storage, area: () -> Double): ParallelCommandGroup() {

    init {
        addCommands(RunShooter(shooter, area), RunStorageConstant(storage, -0.6, RunStorageConstant.STORAGE_TYPE.BOTH))
    }
}