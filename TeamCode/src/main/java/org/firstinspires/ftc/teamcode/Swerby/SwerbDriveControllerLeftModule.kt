package org.firstinspires.ftc.teamcode.Swerby

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.robotcore.external.Telemetry
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot
import kotlin.math.sign


open class SwerbDriveControllerLeftModule(
    private var one: DcMotor,
    private var two: DcMotor,

    private var encoderLeft: DcMotorEx // right pod encoder
) {

    // All the random variables go here:
    val zeroLeft = encoderLeft.currentPosition.toDouble()

    var dashboard: FtcDashboard = FtcDashboard.getInstance()

    var dashboardTelemetry: Telemetry = dashboard.telemetry

    val directionLeft = +1.0

    private var lastErrorLeft = 0.0
    private val TICKS_PER_REV_FOR_POD_ENCODERS = 2666.0

    private fun wrapTo180(angle_left: Double): Double {
        var Angle_of_360_Left = angle_left % 360.0
        if (Angle_of_360_Left > 180.0) Angle_of_360_Left -= 360.0
        if (Angle_of_360_Left < -180.0) Angle_of_360_Left += 360.0
        return Angle_of_360_Left
    }


    private fun angleError(target: Double, current: Double): Double {
        return wrapTo180(target - current)
    }

    fun calculateAngleFromController(x: Double, y: Double): Double {
        if (hypot(x, y) < 0.05) { return 0.0 }
        val angleDegLeft = Math.toDegrees(atan2(y, x))
        return wrapTo180(angleDegLeft)
    }


    fun update(x: Double, y: Double, drivePower: Double) {

        val noSteeringInput = hypot(x, y) < 0.05


        var targetAngleLeft = if (noSteeringInput) {
            0.0
        } else {
            calculateAngleFromController(x, y)
        }

        val rawTicks = encoderLeft.currentPosition.toDouble()
        val relativeTicks = (rawTicks - zeroLeft) * directionLeft
        val angle = wrapTo180((relativeTicks / TICKS_PER_REV_FOR_POD_ENCODERS) * 360.0)

        targetAngleLeft = wrapTo180(targetAngleLeft)

        val leftAngleCurrent = (encoderLeft.currentPosition.toDouble() / TICKS_PER_REV_FOR_POD_ENCODERS) * 360.0
        val currentAngleLeft = wrapTo180(leftAngleCurrent)



        var adjustedDrive = drivePower
        var delta = wrapTo180(targetAngleLeft - angle)



        if (abs(delta) > 90.0) {
            targetAngleLeft = wrapTo180(targetAngleLeft + 180.0)
            adjustedDrive *= -1.0
            delta = angleError(targetAngleLeft, currentAngleLeft)
        }

        val derivative = delta - lastErrorLeft
        lastErrorLeft = delta

        var strafePower = SwerbDriveControllerPID.Left_kP * delta + SwerbDriveControllerPID.Left_kD * derivative

        if (abs(delta) > 1.0 && abs(strafePower) < 0.1) {
            strafePower = 0.1 * sign(strafePower)
        }


        val onePower = adjustedDrive + strafePower
        val twoPower = adjustedDrive - strafePower

        one.power = onePower
        two.power = twoPower
    }
}