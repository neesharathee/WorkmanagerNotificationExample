package net.workmanagerexample

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.work.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = Data.Builder()
                .putString(KEY_TASK_DESC, "Hey I am sending the work data")
                .build()

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val request = OneTimeWorkRequest.Builder(MyWorker::class.java!!)
                .setInputData(data)
                .setConstraints(constraints)
                .build()

        findViewById<View>(R.id.button).setOnClickListener { WorkManager.getInstance().enqueue(request) }

        val textView = findViewById<TextView>(R.id.textView)

        WorkManager.getInstance().getWorkInfoByIdLiveData(request.id)
                .observe(this, Observer { workInfo ->
                    if (workInfo != null) {

                        if (workInfo.state.isFinished) {

                            val data = workInfo.outputData

                            val output = data.getString(MyWorker.KEY_TASK_OUTPUT)

                            textView.append(output!! + "\n")
                        }

                        val status = workInfo.state.name
                        textView.append(status + "\n")
                    }
                })

    }

    companion object {

        val KEY_TASK_DESC = "key_task_desc"
    }
}
