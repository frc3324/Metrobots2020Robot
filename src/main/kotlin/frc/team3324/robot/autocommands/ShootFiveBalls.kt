package frc.team3324.robot.autocommands

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.team3324.robot.storage.Storage
import frc.team3324.robot.storage.commands.RunStorage
import frc.team3324.robot.storage.commands.RunStorageConstant

class StorageFiveBalls(storage: Storage, speed: Double, timeStep: Double) : SequentialCommandGroup() {
    init {
        addCommands(RunStorageConstant(storage, speed).withTimeout(timeStep), WaitCommand(timeStep),
                RunStorageConstant(storage, speed).withTimeout(timeStep), WaitCommand(timeStep),
                RunStorageConstant(storage, speed).withTimeout(timeStep), WaitCommand(timeStep),
                RunStorageConstant(storage, speed).withTimeout(timeStep), WaitCommand(timeStep),
                RunStorageConstant(storage, speed).withTimeout(timeStep), WaitCommand(timeStep))
    }
}