package org.firstinspires.ftc.teamcode.Swerby

import com.acmerobotics.dashboard.config.Config

@Config
class SwerbDriveControllerPID {
    companion object {
        @JvmField
        var Left_kP = 0.025
        @JvmField
        var Left_kD = 0.02
        @JvmField
        var Left_kS = 0.02

        @JvmField
        var Right_kP = 0.025
        @JvmField
        var Right_kD = 0.02
    }
}