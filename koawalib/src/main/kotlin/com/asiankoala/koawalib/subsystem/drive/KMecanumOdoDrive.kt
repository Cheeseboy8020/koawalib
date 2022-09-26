package com.asiankoala.koawalib.subsystem.drive

import com.asiankoala.koawalib.hardware.motor.KMotor
import com.asiankoala.koawalib.subsystem.odometry.Odometry

open class KMecanumOdoDrive(
    fl: KMotor,
    bl: KMotor,
    fr: KMotor,
    br: KMotor,
    private val odometry: Odometry,
    private val shouldTelemetryOdo: Boolean
) : KMecanumDrive(fl, bl, fr, br) {

    val pose get() = odometry.pose
    val vel get() = odometry.velocity

    override fun periodic() {
        super.periodic()

        if (shouldTelemetryOdo) {
            odometry.updateTelemetry()
        }
    }
}