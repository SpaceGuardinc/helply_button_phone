package com.example.callservice

import android.app.job.JobParameters
import android.app.job.JobService
import android.app.job.JobScheduler
import android.app.job.JobInfo
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.example.test.ErrorActivity

class MyService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        if (!Settings.canDrawOverlays(this)) {
            val overlayIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            overlayIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(overlayIntent)
        } else {
            val errorIntent = Intent(this, ErrorActivity::class.java)
            errorIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(errorIntent)
        }
        scheduleNextJob(this)
        return false // No more work to do
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true // Retry if job is interrupted
    }

    companion object {
        private const val JOB_ID = 123
        private const val JOB_INTERVAL: Long = 1000 // 10 seconds

        fun scheduleNextJob(context: Context) {
            val componentName = ComponentName(context, MyService::class.java)
            val jobInfo = JobInfo.Builder(JOB_ID, componentName)
                .setMinimumLatency(JOB_INTERVAL)
                .setOverrideDeadline(JOB_INTERVAL)
                .setPersisted(true)
                .build()

            val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.schedule(jobInfo)
        }
    }
}
