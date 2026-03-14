package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Outreach Robot")
public class OutReachRobot extends LinearOpMode {

    private DcMotor lf;
    private DcMotor lb;
    private DcMotor rb;
    private DcMotor rf;
    private Servo lightRight;
    private Servo lightLeft;
    private ElapsedTime runtime = new ElapsedTime();
    //  private DcMotor launch;
    //   private CRServo lauPush;


    @Override
    public void runOpMode() throws InterruptedException {

        lf = hardwareMap.get(DcMotor.class, "lf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lightRight = hardwareMap.get(Servo.class, "lightRight");
        lightLeft = hardwareMap.get(Servo.class, "lightLeft");
        //    launch = hardwareMap.get(DcMotor.class, "launcher");
        //    lauPush = hardwareMap.get(CRServo.class, "lauPush");

        lf.setDirection(DcMotor.Direction.FORWARD);
        lb.setDirection(DcMotor.Direction.FORWARD);
        rf.setDirection(DcMotor.Direction.REVERSE);
        rb.setDirection(DcMotor.Direction.REVERSE);

        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            double speedFactor = 0.5;
            double t = runtime.seconds();


            if (t >= 20 && t < 29) {
                double cycle = ((t + 1) % 2);
                if (cycle < 0.75) {
                    lightLeft.setPosition(0.28);
                    lightRight.setPosition(0.28);
                } else {
                    lightLeft.setPosition(0.5);
                    lightRight.setPosition(0.5);
                }
            } else if (t >= 29) {
                lightLeft.setPosition(0.28);
                lightRight.setPosition(0.28);

            } else {
                lightLeft.setPosition(0.5);
                lightRight.setPosition(0.5);
            }

                if (runtime.seconds() < 30) {
                    speedFactor = (0.5);

                } else {
                    speedFactor = (0.0);
                }

                if (gamepad2.start && gamepad2.back) {
                    runtime.reset();
                }


            double drive = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            double crab = gamepad1.left_stick_x;


            lf.setPower((drive + turn - crab) * speedFactor);
            lb.setPower((drive + turn + crab) * speedFactor);
            rb.setPower((drive - turn - crab) * speedFactor);
            rf.setPower((drive - turn + crab) * speedFactor);



  /*      if (gamepad1.a) {
            lauPush.setPower(0.6);
            launch.setPower(1.0);
        } else {
            launch.setPower(0.0);
        lauPush.setPower(0.0);
    } */
        }
    }
}