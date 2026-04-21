/*package org.firstinspires.ftc.teamcode.Swerby

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion
import kotlin.math.abs
import  kotlin.math.atan2


@Config
class SwerbButItLowKeyWeird {
        companion object {
            @JvmField
            var kP = 0.0
            @JvmField
            var kD = 0.0
        }
}

    @TeleOp(name = "Swerb I think", group = "Swerb Drive")
    public class Swerby : LinearOpMode() {
        lateinit var one: DcMotor
        lateinit var two: DcMotor


        lateinit var encoderLeft : DcMotorEx


        override fun runOpMode() {
            val hwMap: HardwareMap = BlocksOpModeCompanion.opMode.hardwareMap

            one = hwMap.get(DcMotor::class.java, "one")
            two = hwMap.get(DcMotor::class.java, "two")


            encoderLeft = hwMap.get(DcMotorEx::class.java, "encoderLeft")


            one.setDirection(DcMotorSimple.Direction.FORWARD)
            two.setDirection(DcMotorSimple.Direction.REVERSE)


            one.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
            two.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER


            one.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE)
            two.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE)


            // All the random variables go here:
            var leftPodAngle = encoderLeft.currentPosition.toDouble()



            waitForStart();

            fun wrapTo180(angle: Double): Double {
                var Angle_Of_360 = angle % 360.0
                if (Angle_Of_360 > 180.0) Angle_Of_360 -= 360.0
                if (Angle_Of_360 < -180.0) Angle_Of_360 += 360.0
                return Angle_Of_360
            }

            fun getAngleFromController(x: Double, y: Double):Double {
                val targetAngle = getAngleFromController(x, y)
                val angleDeg = Math.toDegrees(atan2(y, x))

                return wrapTo180( (angleDeg - 90.0))
            }

            fun angleError(target: Double, current: Double): Double {
                return wrapTo180(target - current)
            }

            val noSteeringInput = abs(gamepad1.right_stick_x) < 0.05 && abs(gamepad1.right_stick_y) < 0.05

            val targetAngle = if (noSteeringInput) {
                0.0
            } else {
                getAngleFromController(gamepad1.left_stick_x.toDouble(),-gamepad1.left_stick_y.toDouble())
            }.let { wrapTo180(it) }

            val currentAngle = wrapTo180(leftPodAngle)
            val error = angleError(targetAngle, currentAngle)

            var lastError = error
            val steeringPower = SwerbButItLowKeyWeird.kP * error + SwerbButItLowKeyWeird.kD * (error - lastError)



            while (opModeIsActive()) {

                var OnePower = (drive + turn) * 1.0
                var TwoPower = (drive - turn) * 1.0

                one.setPower(OnePower)
                two.setPower(TwoPower)
            }

        }

    }
*/