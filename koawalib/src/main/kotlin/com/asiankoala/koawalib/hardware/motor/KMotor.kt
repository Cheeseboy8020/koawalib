package com.asiankoala.koawalib.hardware.motor

import com.asiankoala.koawalib.command.commands.LoopCmd
import com.asiankoala.koawalib.control.motor.*
import com.asiankoala.koawalib.control.motor.MotorController
import com.asiankoala.koawalib.control.profile.MotionState
import com.asiankoala.koawalib.hardware.KDevice
import com.asiankoala.koawalib.logger.Logger
import com.asiankoala.koawalib.math.d
import com.asiankoala.koawalib.math.epsilonNotEqual
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.Range
import kotlin.math.absoluteValue

@Suppress("unused")
class KMotor internal constructor(name: String) : KDevice<DcMotorEx>(name) {
    lateinit var encoder: KEncoder
    internal var mode = MotorControlModes.OPEN_LOOP
    internal lateinit var controller: MotorController

    private var powerMultiplier = 1.0
    private var disabled = false
    private val cmd = LoopCmd(this::update).withName("$name motor")

    private fun update() {
        if (mode == MotorControlModes.OPEN_LOOP) return

        controller.updateEncoder()
        controller.update()

        var rawOutput = controller.output

        if (isVoltageCorrected) {
            rawOutput *= (12.0 / lastVoltageRead)
        }

        this.power = rawOutput
    }

    internal var zeroPowerBehavior: DcMotor.ZeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        set(value) {
            device.zeroPowerBehavior = value
            field = value
        }

    internal var direction: DcMotorSimple.Direction = DcMotorSimple.Direction.FORWARD
        set(value) {
            powerMultiplier = if (value == DcMotorSimple.Direction.FORWARD) {
                1.0
            } else {
                -1.0
            }
            field = value
        }

    internal var isVoltageCorrected = false
    internal val rawMotorPosition get() = device.currentPosition.d
    internal val rawMotorVelocity get() = device.velocity

    var power: Double = 0.0
        set(value) {
            var clipped = Range.clip(value, -1.0, 1.0) * powerMultiplier
            if (isVoltageCorrected) clipped *= (12.0 / lastVoltageRead)
            if (clipped epsilonNotEqual field && (clipped == 0.0 || clipped.absoluteValue == 1.0 || (clipped - field).absoluteValue > 0.005)) {
                field = clipped
                device.power = clipped
            }
        }

    val setpoint: MotionState get() {
        if (mode != MotorControlModes.MOTION_PROFILE) {
            throw Exception("controller not motion profile controller")
        } else {
            return (controller as MotionProfileMotorController).setpoint
        }
    }

    val currState: MotionState get() {
        if (mode == MotorControlModes.OPEN_LOOP) {
            throw Exception("controller not closed loop")
        } else {
            return controller.currentState
        }
    }

    val pos: Double get() = encoder.pos

    val vel: Double get() = encoder.vel

    val accel: Double get() = encoder.accel

    fun setPositionTarget(x: Double) {
        if (mode != MotorControlModes.POSITION) throw Exception("motor must be position controlled")
        controller.setTargetPosition(x)
        Logger.logInfo("set motor $deviceName's position target to $x")
    }

    fun setVelocityTarget(v: Double) {
        if (mode != MotorControlModes.VELOCITY) throw Exception("motor must be velocity controlled")
        controller.setTargetVelocity(v)
    }

    fun setProfileTarget(x: Double, v: Double = 0.0) {
        if (mode != MotorControlModes.MOTION_PROFILE) throw Exception("motor must be motion profiled")
        controller.setProfileTarget(x, v)
    }

    fun isAtTarget(): Boolean {
        if (mode == MotorControlModes.OPEN_LOOP) throw Exception("motor must not be open loop")
        return controller.isAtTarget()
    }

    fun enable() {
        power = 0.0
        disabled = false
        if (mode != MotorControlModes.OPEN_LOOP) {
            encoder.enable()
        }
        cmd.schedule()
    }

    fun disable() {
        power = 0.0
        disabled = true
        if (mode != MotorControlModes.OPEN_LOOP) {
            encoder.disable()
        }
        cmd.cancel()
    }

    init {
        device.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        device.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
    }
}