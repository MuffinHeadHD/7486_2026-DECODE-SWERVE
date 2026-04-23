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

@TeleOp(name = "Swerb Encoder reset")
public class SwerbEncoderReset: LinearOpMode() {


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
            var leftEncoderTicks = encoderLeft.currentPosition.toDouble()
            var rightEncoderTicks = encoderRight.currentPosition.toDouble()

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
            dashboardTelemetry.addData("left encoder ticks",leftEncoderTicks )
            dashboardTelemetry.addData("right angle", rightAngleCurrent)
            dashboardTelemetry.addData("right encoder ticks", rightEncoderTicks)
            dashboardTelemetry.addData("right total revolutions", (rightEncoderTicks / SwerbTestingConfig.ticks_per_rev))
            dashboardTelemetry.addData("left total revolutions", (leftEncoderTicks / SwerbTestingConfig.ticks_per_rev))
            dashboardTelemetry.update()

        }
    }
}
