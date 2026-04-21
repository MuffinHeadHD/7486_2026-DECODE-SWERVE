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
        var ticks_per_rev = 28.0
    }
}

@TeleOp(name = "EncoderReadout")
public class SwerbEncoderFinder: LinearOpMode() {

    lateinit var encoderLeft : DcMotorEx

    var dashboard: FtcDashboard = FtcDashboard.getInstance()
    var dashboardTelemetry: Telemetry = dashboard.telemetry

    override fun runOpMode() {

        encoderLeft = hardwareMap.get(DcMotorEx::class.java, "encoderLeft")

        waitForStart();

        while (opModeIsActive()) {
            var leftAngleCurrent = (((encoderLeft.currentPosition.toDouble()) / (SwerbTestingConfig.ticks_per_rev)) * 360)
            var leftEncoderTicks = encoderLeft.currentPosition.toDouble()

            dashboardTelemetry.addData("left angle", leftAngleCurrent)
            dashboardTelemetry.addData("left encoder ticks", leftEncoderTicks)
            dashboardTelemetry.addData("total revolutions", (leftEncoderTicks / SwerbTestingConfig.ticks_per_rev))
            dashboardTelemetry.update()
        }
    }
}
