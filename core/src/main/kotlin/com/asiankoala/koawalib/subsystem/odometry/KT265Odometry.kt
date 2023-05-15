package com.asiankoala.koawalib.subsystem.odometry

import android.os.SystemClock
import com.asiankoala.koawalib.hardware.KDevice.Companion.hardwareMap
import com.asiankoala.koawalib.hardware.sensor.KT265Camera
import com.asiankoala.koawalib.logger.Logger
import com.asiankoala.koawalib.math.Pose
import java.lang.Thread.sleep
import java.util.function.Consumer

class KT265Odometry (
    private val offset: Pose,
    private val cov: Double,
    private val wheelOdo: Odometry? = null,
    startPose: Pose) : Odometry(startPose), Consumer<KT265Camera.Companion.CameraUpdate> {

    // LOCKS //
    private object UpdateMutex

    // SLAMERA STUFF //
    @Volatile private var updatesReceived = 0
    @Volatile private var lastUpdateTime = SystemClock.elapsedRealtimeNanos()
    @Volatile private var updateDelayNanos = 0L

    private fun waitForUpdate() {
        val lastUpdatesReceived = updatesReceived
        while (lastUpdatesReceived == updatesReceived) { continue }
    }

    private var odometryVelocityCallback: (() -> Pose)? = null


    private var lastUpdate: KT265Camera.Companion.CameraUpdate =
        KT265Camera.Companion.CameraUpdate(Pose(), Pose(), KT265Camera.Companion.PoseConfidence.Failed)
        get() {
            synchronized(UpdateMutex) {
                return field
            }
        }
        set(value) {
            synchronized(UpdateMutex){
                field = value
            }
        }

    private val poseConfidence: KT265Camera.Companion.PoseConfidence
        get() {
            return lastUpdate.confidence
        }

    override fun updateTelemetry() {
        Logger.put("------ODO------")
        Logger.put("start pose", startPose)
        Logger.put("curr pose", pose)
        Logger.put("T265", lastUpdate.pose)
        if(wheelOdo!= null) {
            Logger.put("WheelOdo", wheelOdo.pose)
        }
        Logger.put("Confidence: ", poseConfidence)
    }

    override fun reset(p: Pose) {
        lastUpdate.pose = p
        slamera!!.setPose(
            Pose(p.vec.rotate(offset.heading), p.heading)
        )
    }

    override fun periodic() {
        if (wheelOdo!= null) {
            wheelOdo.periodic()
            val odometryVelocity = odometryVelocityCallback?.invoke()
            if (odometryVelocity != null) {
                slamera!!.sendOdometry(
                    odometryVelocity.x,
                    odometryVelocity.y
                )
            }
        }
        waitForUpdate()
        pose = lastUpdate.pose
    }

    companion object {
        // TODO: add logging for the direct values of the camera
        private var persistentSlamera: KT265Camera? = null
        @JvmField var slamera: KT265Camera? = null
    }

    init {
        if (wheelOdo!= null) {
            odometryVelocityCallback = { wheelOdo.vel }
        }
        if (persistentSlamera == null) {
            persistentSlamera = KT265Camera(
                offset,
                cov,
                hardwareMap.appContext
            )
        }
        slamera = persistentSlamera!!
        sleep(1000)
        if (!slamera!!.isStarted()) {
            slamera!!.start(this as Consumer<KT265Camera.Companion.CameraUpdate>)
        }
    }

    override fun accept(update: KT265Camera.Companion.CameraUpdate) {
        updatesReceived++
        val elapsedTimeNanos = SystemClock.elapsedRealtimeNanos()
        updateDelayNanos = elapsedTimeNanos - lastUpdateTime
        lastUpdateTime = elapsedTimeNanos
        synchronized(UpdateMutex) {
            lastUpdate = update
        }
    }
}