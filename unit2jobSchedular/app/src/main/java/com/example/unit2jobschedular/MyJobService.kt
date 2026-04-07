package com.example.unit2jobschedular

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class MyJobService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d("JobScheduler", "Job Started")
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d("JobScheduler", "Job Stopped")
        return true
    }
}