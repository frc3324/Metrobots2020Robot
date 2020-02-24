package frc.team3324.robot.autocommands

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup
import frc.team3324.robot.shooter.Shooter
import frc.team3324.robot.storage.Storage
import frc.team3324.robot.storage.commands.RunStorageConstant

class ShooterAndStorageRaceParallel(shooter: Shooter, storage: Storage, area: () -> Double): ParallelRaceGroup() {

    init {
        addCommands(ShootOneBall(area(), shooter), RunStorageConstant(storage, 0.6, RunStorageConstant.STORAGE_TYPE.BOTH))
    }
}