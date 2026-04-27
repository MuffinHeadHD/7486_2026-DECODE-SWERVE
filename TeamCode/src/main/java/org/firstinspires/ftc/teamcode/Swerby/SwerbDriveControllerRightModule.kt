package org.firstinspires.ftc.teamcode.Swerby

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot
import kotlin.math.sign


open class SwerbDriveControllerRightModule(
    private var three: DcMotor,
    private var four: DcMotor,

    private var encoderRight: DcMotorEx // right pod encoder
) {

    // All the random variables go here:
    val zeroRight = encoderRight.currentPosition.toDouble()

    val directionRight = +1.0

    private var lastErrorRight = 0.0
    private val TICKS_PER_REV_FOR_POD_ENCODERS = 2666.0


    private fun wrapTo180(angleRight: Double): Double {
        var Angle_of_360_right = angleRight % 360.0
        if (Angle_of_360_right > 180.0) Angle_of_360_right -= 360.0
        if (Angle_of_360_right < -180.0) Angle_of_360_right += 360.0
        return Angle_of_360_right
    }

    private fun angleError(target: Double, current: Double): Double {
        return wrapTo180(target - current)
    }

    fun calculateAngleFromController(x: Double, y: Double, t: Double): Double {
        if (hypot(x, y) < 0.05) {
            return 0.0
        }
        val angleDegRight = Math.toDegrees(atan2(y, x))
        return wrapTo180(angleDegRight)
    }


    public fun update(x: Double, y: Double, drivePower: Double, t: Double) {

        val noSteeringInput = hypot(x, y) < 0.05

        var targetAngleRight = if (noSteeringInput) {
            0.0
        } else {
            calculateAngleFromController(x, y, t)
        }


        val rawTicks = encoderRight.currentPosition.toDouble()
        val relativeTicks = (rawTicks - zeroRight) * directionRight
        val angle = wrapTo180((relativeTicks / TICKS_PER_REV_FOR_POD_ENCODERS) * 360.0)



        targetAngleRight = wrapTo180(targetAngleRight)

        val rightAngleCurrent = (((encoderRight.currentPosition.toDouble()) / (TICKS_PER_REV_FOR_POD_ENCODERS)) * 360)
        val currentAngleRight = wrapTo180(rightAngleCurrent)


        var adjustedDrive = drivePower
        var delta = wrapTo180(targetAngleRight - angle)



        if (abs(delta) > 90.0) {
            targetAngleRight = wrapTo180(targetAngleRight + 180.0)
            adjustedDrive *= -1.0
            delta = angleError(targetAngleRight, currentAngleRight)
        }

        val derivative = delta - lastErrorRight
        lastErrorRight = delta


        var turnPower = t
        var strafePower = SwerbDriveControllerPID.Right_kP * delta + SwerbDriveControllerPID.Right_kD * derivative

        if (abs(delta) > 1.0 && abs(strafePower) < 0.1) {
            strafePower = 0.1 * sign(strafePower)
        }


        val threePower = adjustedDrive + strafePower + turnPower
        val fourPower = adjustedDrive - strafePower + turnPower

        three.power = threePower
        four.power = fourPower

        if (threePower < 0.05) { three.power = 0.0 }
        if (fourPower < 0.05) { four.power = 0.0 }
    }
}