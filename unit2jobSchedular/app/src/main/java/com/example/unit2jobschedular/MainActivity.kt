package com.example.unit2jobschedular

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit2jobschedular.ui.theme.Unit2jobSchedularTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           jobScreen()
            }

    }
}

@Composable
fun jobScreen(){
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text  =  "Job Schedular Demo",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.padding(20.dp))

        Button(onClick = {
            scheduleJob(context)
        },
            modifier = Modifier.fillMaxWidth()) {
            Text("start job"
            )
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Button(onClick = {
            cancelJob(context)
        } ,
            modifier = Modifier.fillMaxWidth()) {
            Text("Cancel JOb")
        }
    }
}
fun scheduleJob(context: Context) {

    val componentName = ComponentName(context, MyJobService::class.java)

    val jobInfo = JobInfo.Builder(1, componentName)
        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        .setPeriodic(15 * 60 * 1000) // 15 minutes
        .build()

    val jobScheduler =
        context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

    val result = jobScheduler.schedule(jobInfo)

    if (result == JobScheduler.RESULT_SUCCESS) {
        Toast.makeText(context, "Job Scheduled Successfully", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, "Job Scheduling Failed", Toast.LENGTH_SHORT).show()
    }
}


// ✅ Function to Cancel Job
fun cancelJob(context: Context) {
    val jobScheduler =
        context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

    jobScheduler.cancel(1)
    Toast.makeText(context, "Job Cancelled", Toast.LENGTH_SHORT).show()
}