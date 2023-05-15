package com.asiankoala.koawalib.math

import jetbrains.datalore.base.typedGeometry.Vec

/**
 * Represents robot's position and heading
 * @param[x] x position
 * @param[y] y position
 * @param[heading] heading, in radians
 */
data class Pose @JvmOverloads constructor(
    val x: Double = 0.0,
    val y: Double = 0.0,
    val heading: Double = 0.0
) {
    /**
     * Secondary constructor that takes a vector and heading
     * @param[v] vector
     * @param[h] heading
     */
    constructor(v: Vector, h: Double) : this(v.x, v.y, h)

    /**
     * This pose's position vector
     */
    val vec get() = Vector(x, y)

    /**
     * String of x, y, and heading in degrees
     */
    override fun toString(): String {
        return String.format("%.2f, %.2f, %.2f°", x, y, heading.angleWrap.degrees)
    }


    fun relativeTo(other: Pose): Pose {
        return Pose(this.vec.minus(other.vec).rotate(other.heading.unaryMinus()), this.heading.minus(other.heading))
    }

    fun transformBy(other: Pose): Pose{
        return Pose(this.vec.plus(other.vec.rotate(this.heading)), this.heading.plus(other.heading))
    }
}
