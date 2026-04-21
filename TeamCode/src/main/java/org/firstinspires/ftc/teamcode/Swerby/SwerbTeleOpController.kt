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
        one = hardwareMap.get(DcMotor::class.java, "one")
        two = hardwareMap.get(DcMotor::class.java, "two")
        four = hardwareMap.get(DcMotor::class.java, "three")
        three = hardwareMap.get(DcMotor::class.java, "four")

        encoderLeft = hardwareMap.get(DcMotorEx::class.java, "encoderLeft")
        encoderRight = hardwareMap.get(DcMotorEx::class.java, "encoderRight")

        val controllerLeft = SwerbDriveControllerLeftModule(one, two, encoderLeft)

        val controllerRight = SwerbDriveControllerRightModule(three, four, encoderRight)

        waitForStart();

        while (opModeIsActive()) {
            val x_Left = gamepad1.left_stick_x.toDouble()
            val y_Left = -gamepad1.left_stick_y.toDouble()
            val turn_Left = gamepad1.right_stick_x.toDouble()

            val drive_Left = atan2(gamepad1.left_stick_y.toDouble() /2, gamepad1.left_stick_x.toDouble() /2 )

            controllerLeft.update(x_Left, y_Left, drive_Left)


            val y_Right = gamepad1.left_stick_x.toDouble()
            val x_Right = -gamepad1.left_stick_y.toDouble()
            val turn_right = gamepad1.right_stick_x.toDouble()

            val drive_Right = atan2(gamepad1.left_stick_y.toDouble(), gamepad1.left_stick_x.toDouble())

            controllerRight.update(x_Right, y_Right,drive_Right)

            while (gamepad1.a) {
                one.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
                two.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
                three.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
                four.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

                encoderLeft.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
                encoderRight.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            }

            var leftAngleCurrent = (((encoderLeft.currentPosition.toDouble()) / (SwerbTestingConfig.ticks_per_rev)) * 360)
            var leftEncoderTicks = encoderLeft.currentPosition.toDouble()

            var rightAngleCurrent = (((encoderRight.currentPosition.toDouble()) / (SwerbTestingConfig.ticks_per_rev)) * 360)
            var rightEncoderTicks = encoderRight.currentPosition.toDouble()

            dashboardTelemetry.addData("left angle", leftAngleCurrent)
            dashboardTelemetry.addData("angle from controller for right", Math.toDegrees(atan2(y_Right, x_Right)))
            dashboardTelemetry.addData("drive_Right", drive_Right)
            dashboardTelemetry.addData("drive_Left", drive_Left)
            dashboardTelemetry.addData("angle from controller for left", Math.toDegrees(atan2(y_Left, x_Left)))
            dashboardTelemetry.addData("left encoder ticks",rightEncoderTicks )
            dashboardTelemetry.addData("right angle", rightAngleCurrent)
            dashboardTelemetry.addData("right encoder ticks", rightEncoderTicks)
            dashboardTelemetry.addData("right total revolutions", (rightEncoderTicks / SwerbTestingConfig.ticks_per_rev))
            dashboardTelemetry.addData("left total revolutions", (leftEncoderTicks / SwerbTestingConfig.ticks_per_rev))
            dashboardTelemetry.update()

        }
    }
}
