package com.example.wakeupalarm

import android.provider.AlarmClock
import java.time.LocalTime
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.util.Log

fun setAlarms(context: Context, alarmsNeeded: Array<LocalTime>): Boolean {
    try {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        if (!audioManager.isVolumeFixed) {
            val maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)

            audioManager.setStreamVolume(
                AudioManager.STREAM_ALARM,
                maxVol,
                0
            )
        }


        for (time in alarmsNeeded) {
            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {

                putExtra(
                    AlarmClock.EXTRA_HOUR, time.hour
                )

                putExtra(
                    AlarmClock.EXTRA_MINUTES, time.minute
                )

                putExtra(
                    AlarmClock.EXTRA_SKIP_UI, true
                )

                putExtra(
                    AlarmClock.EXTRA_VIBRATE, true
                )
            }

            val activity = intent.resolveActivity(context.packageManager)
            if (activity != null) {
                context.startActivity(intent)
                Log.d("AlarmSetting", "AlarmHandler {$activity}")
            } else {
                Log.d("AlarmSetting", "No alarm app found")
            }
        }
        return true
    }

    catch (e: Exception){
        Log.e("AlarmSet", e.message.toString())
        return false
    }
}