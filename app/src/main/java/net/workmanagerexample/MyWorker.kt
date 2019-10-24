package net.workmanagerexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat

import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {

        val data = inputData
        val desc = data.getString(MainActivity.KEY_TASK_DESC)
        displayNotification("Hey I am your work", desc)

        val data1 = Data.Builder()
                .putString(KEY_TASK_OUTPUT, "Task Finished Successfully")
                .build()

        outputData = data1

        return Result.SUCCESS
    }

    private fun displayNotification(task: String, desc: String?) {

        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("workmanagerexample", "workmanagerexample", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(applicationContext, "workmanagerexample")
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher)

        manager.notify(1, builder.build())

    }

    companion object {

        val KEY_TASK_OUTPUT = "key_task_output"
    }
}
