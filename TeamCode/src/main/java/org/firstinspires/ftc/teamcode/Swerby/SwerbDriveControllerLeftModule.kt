package org.firstinspires.ftc.teamcode.Swerby

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import kotlin.math.abs
import  kotlin.math.atan2
import kotlin.math.hypot


open class SwerbDriveControllerLeftModule(
    private var one: DcMotor,
    private var two: DcMotor,

    private var encoderLeft: DcMotorEx // left pod encoder
) {

    // All the random variables go here:
    private var lastErrorLeft = 0.0
    private val TICKS_PER_REV_FOR_POD_ENCODERS = 2666.0
    private var leftAngleCurrent = (((encoderLeft.currentPosition.toDouble()) / (TICKS_PER_REV_FOR_POD_ENCODERS)) * 360)

    private fun wrapTo180(angle: Double): Double {
        var Angle_of_360 = angle % 360.0
        if (Angle_of_360 > 180.0) Angle_of_360 -= 360.0
        if (Angle_of_360 < -180.0) Angle_of_360 += 360.0
        return Angle_of_360
    }

    private fun angleError(target: Double, current: Double): Double {
        return wrapTo180(target - current)
    }

    fun calculateAngleFromController(x: Double, y: Double): Double {
        if (hypot(x, y) < 0.05) return 0.0
        val angleDeg = Math.toDegrees(atan2(y, x))
        return wrapTo180(angleDeg)
    }


    fun update(x: Double, y: Double, drivePower: Double) {

        val noSteeringInput = hypot(x, y) < 0.05


        var targetAngle = if (noSteeringInput) {
            0.0
        } else {
            calculateAngleFromController(x, y)
        }

        targetAngle = wrapTo180(targetAngle)

        val currentAngleLeft = wrapTo180(leftAngleCurrent)


        var adjustedDrive = drivePower
        var delta = angleError(targetAngle, currentAngleLeft)

        if (abs(delta) > 90.0) {
            targetAngle = wrapTo180(targetAngle + 180.0)
            adjustedDrive *= -1.0
            delta = angleError(targetAngle, currentAngleLeft)
        }


        val derivative = delta - lastErrorLeft
        val steeringPower = SwerbDriveControllerPID.Left_kP * delta + SwerbDriveControllerPID.Left_kD * derivative
        lastErrorLeft = delta


        val onePower = adjustedDrive + steeringPower
        val twoPower = adjustedDrive - steeringPower

        one.setPower(onePower)
        two.setPower(twoPower)
    }
}