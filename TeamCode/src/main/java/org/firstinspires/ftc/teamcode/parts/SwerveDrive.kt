package org.firstinspires.ftc.teamcode.parts

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot
import kotlin.math.sign

enum class SwerbDriveModule { RIGHT, LEFT }


class SwerveDrive(
    val LeftModule: SwerbDriveModule = SwerbDriveModule.RIGHT,
    val RightModule: SwerbDriveModule = SwerbDriveModule.LEFT
) {
    var onePower = 0.0
    var twoPower = 0.0
    var threePower = 0.0
    var fourPower = 0.0

    var lastTime = System.nanoTime()

    fun LeftModuleDriveModule(one: DcMotor, two: DcMotor, three: DcMotor, four: DcMotor, encoderLeft: DcMotorEx, encoderRight: DcMotorEx) {

    }

    fun home(angleHome: Int) {

    }

    fun DriveSystemUpdate(x_left: Double, y_left: Double, x_right: Double, y_right: Double, drivePowerLeft: Double, drivePowerRight: Double) {
        val now = System.nanoTime()
        val dt = (now - lastTime) / 1e9
        lastTime = now
    }
}

/*

open class SwerbDriveControllerRightModule(
    private var three: DcMotor,
    private var four: DcMotor,

    private var encoderRight: DcMotorEx // right pod encoder
) {

    // All the random variables go here:
    val zeroRight = encoderRight.currentPosition.toDouble()

    val directionRight = -1.0

    private var lastErrorRight = 0.0
    private val TICKS_PER_REV_FOR_POD_ENCODERS = 2660.0


    private fun wrapTo180(angleRight: Double): Double {
        var Angle_of_360_right = angleRight % 360.0
        if (Angle_of_360_right > 180.0) Angle_of_360_right -= 360.0
        if (Angle_of_360_right < -180.0) Angle_of_360_right += 360.0
        return Angle_of_360_right
    }

    private fun angleError(target: Double, current: Double): Double {
        return wrapTo180(target - current)
    }

    fun calculateAngleFromController(x: Double, y: Double): Double {
        if (hypot(x, y) < 0.1) { return 0.0 }
        val angleDegRight = Math.toDegrees(atan2(y, x))
        return wrapTo180(angleDegRight)
    }




    public fun update(x: Double, y: Double, drivePower: Double) {

        val noSteeringInput = hypot(x, y) < 0.1

        var targetAngleRight = if (noSteeringInput) {
            0.0
        } else {
            calculateAngleFromController(x, y)
        }


        val rawTicks = encoderRight.currentPosition.toDouble()
        val relativeTicks = (rawTicks - zeroRight) * directionRight
        val angle = wrapTo180((relativeTicks / TICKS_PER_REV_FOR_POD_ENCODERS) * 360.0)



        targetAngleRight = wrapTo180(targetAngleRight)

        val rightAngleCurrent  = (((encoderRight.currentPosition.toDouble()) / (TICKS_PER_REV_FOR_POD_ENCODERS)) * 360)

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

        var strafePower = SwerbDriveControllerPID.Right_kP * delta + SwerbDriveControllerPID.Right_kD * derivative

        if (abs(delta) > 1.0 && abs(strafePower) < 0.1) {
            strafePower = 0.05 * sign(strafePower)
        }


        val threePower = adjustedDrive + strafePower
        val fourPower = adjustedDrive - strafePower

        three.power = threePower
        four.power = fourPower
        }

}

/*
package org.firstinspires.ftc.teamcode.Swerby

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import kotlin.math.abs
import  kotlin.math.atan2
import kotlin.math.hypot


class SwerbDriveControllerRightModule(
    private var three: DcMotor,
    private var four: DcMotor,

    private var encoderRight: DcMotorEx // right pod encoder
) {

    // All the random variables go here:
    val zeroRight = encoderRight.currentPosition.toDouble()

    val directionRight = -1.0

    private var lastErrorRight = 0.0
    private val TICKS_PER_REV_FOR_POD_ENCODERS = 2666.0

    private fun wrapTo180(angle_right: Double): Double {
        var Angle_of_360_right = angle_right % 360.0
        if (Angle_of_360_right > 180.0) Angle_of_360_right -= 360.0
        if (Angle_of_360_right < -180.0) Angle_of_360_right += 360.0
        return Angle_of_360_right
    }

    private fun angleError(target: Double, current: Double): Double {
        return wrapTo180(target - current)
    }

    fun calculateAngleFromController(x: Double, y: Double): Double {
        if (hypot(x, y) < 0.1) { return 0.0 }
        val angleDegRight = Math.toDegrees(atan2(x, y))
        return wrapTo180(angleDegRight)
    }


    fun update(x: Double, y: Double, drivePower: Double) {

        val rawTicks = encoderRight.currentPosition.toDouble()
        val relativeTicks = (rawTicks - zeroRight) * directionRight
        val angle = wrapTo180((relativeTicks / TICKS_PER_REV_FOR_POD_ENCODERS) * 360.0)


        val noSteeringInput = hypot(x, y) < 0.1


        var targetAngleRight = if (noSteeringInput) {
            0.0
        } else {
            calculateAngleFromController(x, y)
        }

        targetAngleRight = wrapTo180(targetAngleRight)

        var rightAngleCurrent = (((encoderRight.currentPosition.toDouble()) / (TICKS_PER_REV_FOR_POD_ENCODERS)) * 360)

        val currentAngleRight = wrapTo180(rightAngleCurrent)


        var adjustedDrive = drivePower
        var delta = angleError(targetAngleRight, currentAngleRight)


        if (abs(delta) > 90.0) {
            targetAngleRight = wrapTo180(targetAngleRight + 180.0)
            adjustedDrive *= -1.0
            delta = angleError(targetAngleRight, currentAngleRight)
        }


        val derivative = delta - lastErrorRight
        val strafePower = SwerbDriveControllerPID.Right_kP * delta + SwerbDriveControllerPID.Right_kD * derivative
        lastErrorRight = delta


        val threePower = adjustedDrive + strafePower
        val fourPower = adjustedDrive - strafePower

        three.setPower(threePower)
        four.setPower(fourPower)
    }
}

 */