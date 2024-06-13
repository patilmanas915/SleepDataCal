package com.example.sleepdatacal.Screen1

import androidx.lifecycle.ViewModel
import com.example.sleepdatacal.model.SleepData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.Duration
import java.time.LocalDateTime

class MainScreenViewModel : ViewModel() {
    private val _sleepData: MutableStateFlow<SleepData> = MutableStateFlow(SleepData())
    val sleepData: StateFlow<SleepData> get() = _sleepData

    private var startTime: LocalDateTime? = null

    fun incrementCounter() {
        _sleepData.value = _sleepData.value.copy(unlockCount = _sleepData.value.unlockCount + 1)

    }

    fun startTrackingTime() {
        startTime = LocalDateTime.now()
        _sleepData.value = _sleepData.value.copy(TotalSleepTime = Duration.ZERO)
        _sleepData.value = _sleepData.value.copy(unlockCount = 0)

    }

    fun stopTrackingTime() {

        startTime?.let {
            val elapsedTime = Duration.between(it, LocalDateTime.now())
            val unlockCount = _sleepData.value.unlockCount
            val totalSleepTime = elapsedTime - Duration.ofMinutes((unlockCount + 4).toLong())
            val totalSleepTimeMinutes = totalSleepTime.toMinutes().toDouble()

            val sleepCycle = totalSleepTimeMinutes / 90
            val ts1and2 = totalSleepTimeMinutes / 2
            val stage1 = totalSleepTimeMinutes * 0.05
            val stage2 = totalSleepTimeMinutes * 0.50
            val stage3 = (0.30 * ts1and2) + (0.10 * ts1and2)
            val rem = (0.10 * ts1and2) + (0.35 * ts1and2)
            val adStage3 = stage3 - (stage3 * 0.05 * unlockCount)
            val adRem = rem - (rem * 0.06 * unlockCount)
            _sleepData.value = _sleepData.value.copy(
                TotalSleepTime = elapsedTime,
                unlockCount = unlockCount,
                SleepTime = totalSleepTime,
                sleepCycle = sleepCycle,
                stage1 = stage1,
                stage2 = stage2,
                stage3 = stage3,
                TS1and2 = ts1and2,
                REM = rem,
                AdStage3 =  adStage3,
                AdREM = adRem
            )

        }
    }


}