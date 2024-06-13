package com.example.sleepdatacal

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.sleepdatacal.Screen1.MainScreenViewModel

import com.example.sleepdatacal.ui.theme.SleepDataCalTheme
import java.time.Duration

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainScreenViewModel>()
    private val unlockReceiver by lazy { UnlockReceiver(viewModel)}
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SleepDataCalTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var isMonitoring by remember { mutableStateOf(false) }
                    val sleepdata by viewModel.sleepData.collectAsState()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Unlock Count: ${sleepdata.unlockCount}")
                        Spacer(modifier = Modifier.height(20.dp))
                        sleepdata.TotalSleepTime?.let {
                            Text(text = "Tracking Time: ${formatDuration(it)}")
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                        Button(
                            onClick = {
                                if (!isMonitoring) {
                                    startMonitoring()
                                    isMonitoring = true
                                }
                            }
                        ) {
                            Text("Start")
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = {
                                if (isMonitoring) {
                                    stopMonitoring()
                                    isMonitoring = false
                                }
                            }
                        ) {
                            Text("Stop")
                        }
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            item(){
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(text = "Sleep Data:")
                                Text(text = "Sleep Time: ${formatDuration(sleepdata.SleepTime)}")
                                Text(text = "Unlock Count: ${sleepdata.unlockCount}")
                                Text(text = "Total Sleep Time: ${formatDuration(sleepdata.TotalSleepTime)}")
                                Text(text = "Sleep Cycle: ${sleepdata.sleepCycle}")
                                Text(text = "Stage 1: ${sleepdata.stage1}")
                                Text(text = "Stage 2: ${sleepdata.stage2}")
                                Text(text = "Stage 3: ${sleepdata.stage3}")
                                Text(text = "TS1 and 2: ${sleepdata.TS1and2}")
                                Text(text = "REM: ${sleepdata.REM}")
                                Text(text = "AdStage3: ${sleepdata.AdStage3}")
                                Text(text = "AdREM: ${sleepdata.AdREM}")
                            }

                        }
                    }
                }
            }
        }
    }
    private fun startMonitoring() {
        viewModel.startTrackingTime()
        val filter = IntentFilter(Intent.ACTION_USER_PRESENT)
        registerReceiver(unlockReceiver, filter)
        Toast.makeText(this, "Started Monitoring", Toast.LENGTH_SHORT).show()
    }
    private fun stopMonitoring() {
        try {
            unregisterReceiver(unlockReceiver)
        } catch (e: IllegalArgumentException) {
            // Receiver was probably not registered, ignore this case
        }
        viewModel.stopTrackingTime()
        Toast.makeText(this, "Stopped Monitoring", Toast.LENGTH_SHORT).show()

    }
    @SuppressLint("DefaultLocale")
    private fun formatDuration(duration: Duration): String {
        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60
        val seconds = duration.seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
