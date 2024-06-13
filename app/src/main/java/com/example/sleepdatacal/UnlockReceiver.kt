package com.example.sleepdatacal

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.sleepdatacal.Screen1.MainScreenViewModel

class UnlockReceiver(private val viewModel: MainScreenViewModel) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_USER_PRESENT == intent.action) {
            viewModel.incrementCounter()
            val unlockCount = viewModel.sleepData.value.unlockCount
            Toast.makeText(context, "Phone Unlocked $unlockCount times", Toast.LENGTH_SHORT).show()
        }
    }
}