package com.example.wakeupalarm
import android.util.Log
import com.example.wakeupalarm.ui.theme.WakeUpAlarmTheme
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

fun generateAlarmTimes(
    editTextTime: String,
    readyTime: Int = 0
): Array<LocalTime> {

    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    // User's target time
    val targetTime = LocalTime.parse(editTextTime, formatter)

    // wakeUpTime = targetTime - 20 - readyTime
    val wakeUpTime = targetTime.minusMinutes((20 + readyTime).toLong())

    val alarmTimes = mutableListOf<LocalTime>()

    // Start generating from wakeUpTime
    var currentTime = wakeUpTime
    alarmTimes.add(wakeUpTime)

    // Add the two explicitly required times
    alarmTimes.add(targetTime.minusMinutes(10))
    alarmTimes.add(targetTime.minusMinutes(5))

    while (true) {

        // Random interval between 2 and 5 minutes
        val interval = Random.nextInt(2, 5)

        val nextTime = currentTime.plusMinutes(interval.toLong())

        // Do not go beyond wakeUpTime + 5
        if (nextTime > wakeUpTime.plusMinutes(25)) {
            break
        }

        alarmTimes.add(nextTime)
        currentTime = nextTime
    }

    Log.d("Times for Alarms", alarmTimes.toString() + ((alarmTimes.size)-2))

    // Sort chronologically and remove duplicates
    return alarmTimes
        .distinct()
        .sorted()
        .toTypedArray()
}
