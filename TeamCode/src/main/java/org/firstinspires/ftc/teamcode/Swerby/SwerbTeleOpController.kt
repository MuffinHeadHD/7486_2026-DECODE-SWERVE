package org.firstinspires.ftc.teamcode.Swerby;

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.parts.Updatable
import kotlin.math.hypot
import kotlin.math.atan2

@Config
class SwerbThingConfig {
    companion object {
        @JvmField
        var Left_X_sign = 1.0
        @JvmField
        var Left_Y_sign = 1.0
        @JvmField
        var Right_X_sign = -1.0
        @JvmField
        var Right_Y_sign = -1.0
        @JvmField
        var RightTurnSign = 1.0
        @JvmField
        var LeftTurnSign = -1.0
    }
}

@TeleOp(name = "SwerbTeleOpController")
public class SwerbTeleOpController: LinearOpMode() {


    lateinit var one: DcMotor
    lateinit var two: DcMotor
    lateinit var three: DcMotor
    lateinit var four: DcMotor
    lateinit var encoderLeft: DcMotorEx
    lateinit var encoderRight: DcMotorEx

    var dashboard: FtcDashboard = FtcDashboard.getInstance()
    var dashboardTelemetry: Telemetry = dashboard.telemetry


    override fun runOpMode() {
        one = hardwareMap.get(DcMotor::class.java, "one") //left
        two = hardwareMap.get(DcMotor::class.java, "two") //left
        three = hardwareMap.get(DcMotor::class.java, "three") //right
        four = hardwareMap.get(DcMotor::class.java, "four") //right
        /*
        one.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        two.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        three.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        four.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

         */


        encoderLeft = hardwareMap.get(DcMotorEx::class.java, "encoderLeft")
        encoderRight = hardwareMap.get(DcMotorEx::class.java, "encoderRight")

        val controllerLeft = SwerbDriveControllerLeftModule(one, two, encoderLeft)

        val controllerRight = SwerbDriveControllerRightModule(three, four, encoderRight)

        waitForStart();

        while (opModeIsActive()) {

            var leftEncoderTicks = encoderLeft.currentPosition.toDouble()
            var rightEncoderTicks = encoderRight.currentPosition.toDouble()

            val yLeft = ((gamepad1.left_stick_x.toDouble()) * SwerbThingConfig.Left_Y_sign)
            val xLeft = ((gamepad1.left_stick_y.toDouble()) * SwerbThingConfig.Left_X_sign)
            val turn_Left = ((gamepad1.right_stick_x.toDouble()) * SwerbThingConfig.LeftTurnSign)

            val drive_Left = (hypot(gamepad1.left_stick_y.toDouble(), gamepad1.left_stick_x.toDouble()))

            controllerLeft.update(xLeft,yLeft, drive_Left, turn_Left)


            val yRight = ((gamepad1.left_stick_x.toDouble()) * SwerbThingConfig.Right_Y_sign)
            val xRight = ((gamepad1.left_stick_y.toDouble()) * SwerbThingConfig.Right_X_sign)
            val turn_right = ((gamepad1.right_stick_x.toDouble()) * SwerbThingConfig.RightTurnSign)

            val drive_Right = (hypot(gamepad1.left_stick_y.toDouble(), gamepad1.left_stick_x.toDouble()))

            controllerRight.update(xRight, yRight,drive_Right, turn_right)

            if (turn_right > 0.2) {
                turn_right * 2
            } else {
                turn_right * 1
            }

            if (turn_Left > 0.2) {
                turn_Left * 2
            } else {
                turn_Left * 1
            }


            if (gamepad1.a) {
                one.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
                two.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
                three.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
                four.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

                encoderLeft.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
                encoderRight.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            }


            var leftAngleCurrent = (((encoderLeft.currentPosition.toDouble()) / (SwerbTestingConfig.ticks_per_rev)) * 360)

            var rightAngleCurrent = (((encoderRight.currentPosition.toDouble()) / (SwerbTestingConfig.ticks_per_rev)) * 360)

            dashboardTelemetry.addData("left angle", leftAngleCurrent)
            dashboardTelemetry.addData("angle from controller for right", Math.toDegrees(atan2(yRight, xRight)))
            dashboardTelemetry.addData("drive_Right", drive_Right)
            dashboardTelemetry.addData("drive_Left", drive_Left)
            dashboardTelemetry.addData("angle from controller for left", Math.toDegrees(atan2(xLeft, yLeft)))
            dashboardTelemetry.addData("left encoder ticks",leftEncoderTicks )
            dashboardTelemetry.addData("right angle", rightAngleCurrent)
            dashboardTelemetry.addData("right encoder ticks", rightEncoderTicks)
            dashboardTelemetry.addData("right total revolutions", (rightEncoderTicks / SwerbTestingConfig.ticks_per_rev))
            dashboardTelemetry.addData("left total revolutions", (leftEncoderTicks / SwerbTestingConfig.ticks_per_rev))
            dashboardTelemetry.update()

        }
    }
}
