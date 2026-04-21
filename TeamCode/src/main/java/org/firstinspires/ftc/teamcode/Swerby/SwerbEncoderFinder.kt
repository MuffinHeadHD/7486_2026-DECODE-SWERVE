package org.firstinspires.ftc.teamcode.Swerby

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion
import org.firstinspires.ftc.robotcore.external.Telemetry


@Config
class SwerbTestingConfig {
    companion object {
        @JvmField
        var ticks_per_rev = 2666.0
    }
}

@TeleOp(name = "EncoderReadout")
public class SwerbEncoderFinder: LinearOpMode() {

    lateinit var encoderLeft : DcMotorEx
    lateinit var encoderRight : DcMotorEx

    var dashboard: FtcDashboard = FtcDashboard.getInstance()
    var dashboardTelemetry: Telemetry = dashboard.telemetry

    override fun runOpMode() {

        encoderLeft = hardwareMap.get(DcMotorEx::class.java, "encoderLeft")
        encoderRight = hardwareMap.get(DcMotorEx::class.java, "encoderRight")

        waitForStart();

        while (opModeIsActive()) {
            var leftAngleCurrent = (((encoderLeft.currentPosition.toDouble()) / (SwerbTestingConfig.ticks_per_rev)) * 360)
            var leftEncoderTicks = encoderLeft.currentPosition.toDouble()

            var rightAngleCurrent = (((encoderRight.currentPosition.toDouble()) / (SwerbTestingConfig.ticks_per_rev)) * 360)
            var rightEncoderTicks = encoderRight.currentPosition.toDouble()

            dashboardTelemetry.addData("left angle", leftAngleCurrent)
            dashboardTelemetry.addData("left encoder ticks", leftEncoderTicks)
            dashboardTelemetry.addData("right angle", rightAngleCurrent)
            dashboardTelemetry.addData("right encoder ticks", rightEncoderTicks)
            dashboardTelemetry.addData("right total revolutions", (rightEncoderTicks / SwerbTestingConfig.ticks_per_rev))
            dashboardTelemetry.addData("left total revolutions", (leftEncoderTicks / SwerbTestingConfig.ticks_per_rev))
            dashboardTelemetry.update()
        }
    }
}
