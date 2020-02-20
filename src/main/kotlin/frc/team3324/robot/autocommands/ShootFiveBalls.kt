package frc.team3324.robot.autocommands

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.team3324.robot.shooter.Shooter
import frc.team3324.robot.storage.Storage
import frc.team3324.robot.storage.commands.RunStorage
import frc.team3324.robot.storage.commands.RunStorageConstant

class ShootFiveBalls(shooter: Shooter, storage: Storage, area: () -> Double, waitTime:Double) : SequentialCommandGroup() {
    init {
        addCommands(ShooterAndStorageRaceParallel(shooter, storage, area), WaitCommand(waitTime),
                ShooterAndStorageRaceParallel(shooter, storage, area), WaitCommand(waitTime),
                ShooterAndStorageRaceParallel(shooter, storage, area), WaitCommand(waitTime),
                ShooterAndStorageRaceParallel(shooter, storage, area), WaitCommand(waitTime),
                ShooterAndStorageRaceParallel(shooter, storage, area), WaitCommand(waitTime))
    }
}