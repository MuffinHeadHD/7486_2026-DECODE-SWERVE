package org.firstinspires.ftc.teamcode.auto

import com.acmerobotics.roadrunner.ParallelAction
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.RaceAction
import com.acmerobotics.roadrunner.SequentialAction
import com.acmerobotics.roadrunner.SleepAction
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.AutonomousRobot
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive

@Autonomous(name = "BasicBlueFar")
class BasicBlue: LinearOpMode() {
    lateinit var robot: AutonomousRobot
    val initialPose: Pose2d = Pose2d(64.0, -15.0, Math.toRadians(180.0))

    override fun runOpMode() {
        robot = AutonomousRobot(this)
        val drive = MecanumDrive(hardwareMap, initialPose)

        robot.turret.startLimelight()

        waitForStart()

        robot.spindexer.home()

        val launchAll = RaceAction(
            robot.Update(),
            SequentialAction(
                robot.SpindexerRotate(1),
                SleepAction(2.0),
                robot.SpindexerRotate(1),
                SleepAction(2.0),
                robot.SpindexerRotate(1),
                SleepAction(2.0)
            )
        )

        runBlocking(launchAll)

        val action = RaceAction(
            robot.Update(),
            drive.actionBuilder(initialPose)
                .splineTo(Vector2d(37.0, -30.0), Math.toRadians(-90.0))
                .stopAndAdd(robot.SetIntakeIn())
                .stopAndAdd(SleepAction(0.5))
                .splineTo(Vector2d(37.0, -37.0), Math.toRadians(-90.0))
                .stopAndAdd(robot.SpindexerRotate(-1))
                .splineTo(Vector2d(37.0, -41.0), Math.toRadians(-90.0))
                .stopAndAdd(SleepAction(0.5))
                .stopAndAdd(robot.SpindexerRotate(-1))
                .splineTo(Vector2d(37.0, -61.0), Math.toRadians(-90.0))
                .stopAndAdd(SleepAction(0.5))
                .splineToLinearHeading(initialPose, Math.PI / 2)
                .build()
        )

        runBlocking(action)

        runBlocking(launchAll)
    }
}