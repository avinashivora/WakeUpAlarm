package com.example.wakeupalarm

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.app.TimePickerDialog
import android.content.Intent
import android.provider.AlarmClock
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wakeupalarm.ui.theme.WakeUpAlarmTheme
import kotlinx.coroutines.awaitAll
import java.time.LocalTime

class MainActivity : ComponentActivity() {
    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_file)

        val editTextTime = findViewById<EditText>(R.id.editTextTime)
        val readyTime = findViewById<EditText>(R.id.readyTime)
        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)

        editTextTime.setOnClickListener {
            val timePicker = TimePickerDialog(
                this,
                {_, hourOfDay, minute ->
                    val selectedTime = String.format(
                        "%02d:%02d", hourOfDay, minute
                    )
                    editTextTime.setText(selectedTime)
                },
                5,
                25,
                false
            )
            timePicker.show()
        }


        button.setOnClickListener {
            val timeString = editTextTime.text.toString()
            val readyTimeString = readyTime.text.toString()

            if (timeString.isEmpty()) {
                textView.text = "Please enter time to leave!"
                editTextTime.error = "Please Select a Time"
                return@setOnClickListener
            }

            if (readyTimeString.isEmpty()) {
                textView.text = "Please enter time to get ready!"
                editTextTime.error = "Please Enter a Time"
                return@setOnClickListener
            }

            val readyMinutes = readyTimeString.toInt()

            val alarmTimes = generateAlarmTimes(
                timeString,
                readyMinutes
            )

            setAlarms(
                this,
                alarmTimes
            )

            textView.text = "Alarms are set!"

            return@setOnClickListener
        }
    }
}
