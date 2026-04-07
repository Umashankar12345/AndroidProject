package com.example.alarmset

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import java.util.Calendar

// IMPORTANT: This class must exist for the Manifest <activity android:name=".MainActivity"> to work
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                AlarmScreen()
            }
        }
    }
}

// This matches <receiver android:name=".AlarmReceiver"> in your Manifest
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        try {
            val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val ringtone = RingtoneManager.getRingtone(context, alarmUri)
            ringtone.play()

            Toast.makeText(context, "⏰ Alarm Triggered!", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Alarm Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun AlarmScreen() {
    val context = LocalContext.current
    var hour by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) }
    var minute by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.MINUTE)) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Alarm Manager", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        AndroidView(factory = { ctx ->
            TimePicker(ctx).apply {
                setOnTimeChangedListener { _, h, m ->
                    hour = h
                    minute = m
                }
            }
        })

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)

                if (before(Calendar.getInstance())) {
                    add(Calendar.DATE, 1)
                }
            }

            if (checkExactAlarmPermission(context)) {
                setAlarm(context, calendar.timeInMillis)
            } else {
                requestExactAlarmPermission(context)
            }
        }) {
            Text("Set Alarm")
        }
    }
}

fun checkExactAlarmPermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.canScheduleExactAlarms()
    } else true
}

fun requestExactAlarmPermission(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        try {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Enable permission in settings", Toast.LENGTH_LONG).show()
        }
    }
}

@SuppressLint("ScheduleExactAlarm")
fun setAlarm(context: Context, triggerAtMillis: Long) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)

    val pendingIntent = PendingIntent.getBroadcast(
        context, 0, intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
    Toast.makeText(context, "Alarm Set for ${triggerAtMillis}", Toast.LENGTH_SHORT).show()
}