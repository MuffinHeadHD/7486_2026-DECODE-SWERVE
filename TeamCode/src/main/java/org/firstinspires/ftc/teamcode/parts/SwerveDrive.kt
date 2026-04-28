
package org.firstinspires.ftc.teamcode.parts

import android.view.WindowInsets
import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot
import kotlin.math.sign

enum class SwerbDriveModule(side: Any) {

}


class SwerveDrive(
    val LeftModule:
    val RightModule:
) {
    var onePower = 0.0
    var twoPower = 0.0
    var threePower = 0.0
    var fourPower = 0.0

    var lastTime = System.nanoTime()

    fun LeftwrapTo180(angle_Left: Double): Double {
        var Angle_of_360_Left = angle_Left % 360.0
        if (Angle_of_360_Left > 180.0) Angle_of_360_Left -= 360.0
        if (Angle_of_360_Left < -180.0) Angle_of_360_Left += 360.0
        return Angle_of_360_Left
    }

    fun RightwrapTo180(angle_Right: Double): Double {
        var Angle_of_360_right = angle_Right % 360.0
        if (Angle_of_360_right > 180.0) Angle_of_360_right -= 360.0
        if (Angle_of_360_right < -180.0) Angle_of_360_right += 360.0
        return Angle_of_360_right
    }

    fun LeftModuleDriveModule( three: DcMotor, four: DcMotor, encoderLeft: DcMotorEx) {
        fun LeftangleError(Lefttarget: Double, Leftcurrent: Double): Double {
            return LeftwrapTo180(Lefttarget - Leftcurrent)
        }
    }


    fun calculateAngleFromController(x: Double, y: Double, t: Double): Double {
        if (hypot(x, y) < 0.05) { return 0.0 }
        val angleDegLeft = Math.toDegrees(atan2(y, x))
        return LeftwrapTo180(angleDegLeft)
    }

    fun home(angleHome: Int) {
        targetAngleRight = wrapTo180(angleHome)
        targetAngleLeft = wrapTo180(angleHome)

    }

    fun DriveSystemUpdate(x_left: Double, y_left: Double, x_right: Double, y_right: Double, drivePowerLeft: Double, drivePowerRight: Double) {
        val now = System.nanoTime()
        val dt = (now - lastTime) / 1e9
        lastTime = now
    }
}