package com.asiankoala.koawalib.path

import com.asiankoala.koawalib.command.commands.Command
import com.asiankoala.koawalib.math.MathUtil.d
import com.asiankoala.koawalib.math.MathUtil.radians
import com.asiankoala.koawalib.math.Point

data class Waypoint(
    val x: Double = 0.0,
    val y: Double = 0.0,
    val followDistance: Double = 0.0,
    val maxMoveSpeed: Double = 1.0,
    val maxTurnSpeed: Double = 1.0,
    val stop: Boolean = true,
    val isHeadingLocked: Boolean = false,
    val headingLockAngle: Double = 0.0,
    val slowDownTurnRadians: Double = 60.0.radians,
    val lowestSlowDownFromTurnError: Double = 0.4,
    val turnLookaheadDistance: Double = followDistance,
    val command: Command? = null
) {
    constructor(x: Int, y: Int, followDistance: Int) : this(x.d, y.d, followDistance.d)
    val point = Point(x, y)

    val copy: Waypoint
        get() = Waypoint(
            x, y, followDistance, maxMoveSpeed, maxTurnSpeed,
            stop, isHeadingLocked, headingLockAngle, slowDownTurnRadians,
            lowestSlowDownFromTurnError, turnLookaheadDistance, command
        )
}