package com.asiankoala.koawalib.command.group

import com.asiankoala.koawalib.command.commands.Command
import java.util.*

open class ParallelCommandGroup(vararg commands: Command) : CommandGroupBase() {
    private val mCommands = HashMap<Command, Boolean>()
    private var mRunsWhenDisabled = false

    override fun addCommands(vararg commands: Command) {
        requireUngrouped(*commands)

        if (mCommands.containsValue(true)) {
            throw IllegalStateException("Commands cannot be added to a CommandGroup while the group is running")
        }

        registerGroupedCommands(*commands)

        commands.forEach {
            if (!Collections.disjoint(it.getRequirements(), mRequirements)) {
                throw IllegalStateException("Multiple commands in a parallel group cannot require the same subsystems")
            }

            mCommands[it] = false
            mRequirements.addAll(it.getRequirements())
            mRunsWhenDisabled = mRunsWhenDisabled && it.runsWhenDisabled
        }
    }

    override fun init() {
        for (entry in mCommands.entries) {
            entry.key.init()
            entry.setValue(true)
        }
    }

    override fun execute() {
        for (commandRunning in mCommands.entries) {
            if (!commandRunning.value) {
                continue
            }
            commandRunning.key.execute()
            if (commandRunning.key.isFinished) {
                commandRunning.key.end(false)
                commandRunning.setValue(false)
            }
        }
    }

    override fun end(interrupted: Boolean) {
        if (interrupted) {
            for ((key, value) in mCommands.entries) {
                if (value) {
                    key.end(true)
                }
            }
        }
    }

    override val isFinished: Boolean get() = !mCommands.values.contains(true)
    override val runsWhenDisabled: Boolean get() = mRunsWhenDisabled

    init {
        addCommands(*commands)
    }
}
