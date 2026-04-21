package org.firstinspires.ftc.teamcode.Swerby;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.teamcode.Swerby.SwerbDriveController
import kotlin.math.atan2
import kotlin.math.hypot

@TeleOp(name = "SwerbTeleOpController")
public class SwerbTeleOpController: LinearOpMode() {

    lateinit var one: DcMotor
    lateinit var two: DcMotor
    lateinit var encoderLeft: DcMotorEx


    override fun runOpMode() {

        one = hardwareMap.get(DcMotor::class.java, "one")
        two = hardwareMap.get(DcMotor::class.java, "two")

        encoderLeft = hardwareMap.get(DcMotorEx::class.java, "encoderLeft")

        val controller = SwerbDriveController(one, two, encoderLeft)

        waitForStart();

        while (opModeIsActive()) {
            val x = gamepad1.left_stick_x.toDouble()
            val y = -gamepad1.left_stick_y.toDouble()

            val drive = (hypot(x,y)/2)

            controller.update(x, y, drive)
        }
    }
}
