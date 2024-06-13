package com.example.sleepdatacal.model

import java.time.Duration

data class SleepData(
    val SleepTime: Duration = Duration.ZERO,
    var unlockCount: Int = 0,
    var TotalSleepTime: Duration = Duration.ZERO,
    val sleepCycle: Double = 0.0,
    val stage1: Double = 0.0,
    val stage2: Double = 0.0,
    val stage3: Double = 0.0,
    val TS1and2: Double = 0.0,
    val REM: Double = 0.0,
    val AdStage3: Double = 0.0,
    val AdREM: Double = 0.0,
)
