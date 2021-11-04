package qu.cmps312.workmanager

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.work.*
import qu.cmps312.workmanager.ui.theme.WorkManagerTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkManagerTheme {
                val context = LocalContext.current
                var jobState by remember { mutableStateOf("") }
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen(jobState, onStartClicked = {
                        setOneTimeWorkRequest(context, onJobStateChange = {
                            jobState = it
                        })
                        //setPeriodicWorkRequest()
                    })
                }
            }
        }
    }

    private fun setOneTimeWorkRequest(context: Context, onJobStateChange: (String) -> Unit) {
        val workManager = WorkManager.getInstance(context)

        val inputData = workDataOf(Constants.COUNT_VALUE to 125)

        val constraints = Constraints.Builder()
            //.setRequiresDeviceIdle(true)
            //.setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        //1000, 2000, 3000
        val uploadRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .addTag(Constants.UPLOAD_TAG)
            .setConstraints(constraints)
            .setInputData(inputData)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                1000,
                TimeUnit.SECONDS)
            .build()

        val filterRequest = OneTimeWorkRequestBuilder<FilterWorker>().build()

        val compressRequest= OneTimeWorkRequestBuilder<CompressWorker>().build()

        val downloadRequest= OneTimeWorkRequestBuilder<DownloadWorker>().build()
        //WorkManager.getInstance(applicationContext).enqueue(downloadRequest)

        val parallelWorks = listOf(downloadRequest, filterRequest)
        /* workManager.beginWith(parallelWorks)
                    .then(compressRequest)
                    .then(uploadRequest)
                    .enqueue()*/

        workManager.enqueue(uploadRequest)
        val workInfo = workManager.getWorkInfosByTag(Constants.UPLOAD_TAG)
        workInfo.get()[0].state.isFinished
        println(">> Debug: ${workInfo.get()[0].state}")
        workManager.getWorkInfoByIdLiveData(uploadRequest.id)
            .observe(this, Observer {
                onJobStateChange(it.state.name)
                if(it.state.isFinished) {
                    val data = it.outputData
                    val message = data.getString(Constants.CURRENT_DATE)
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                }
            })


        // Observing Worker Progress
        val request = OneTimeWorkRequestBuilder<ProgressWorker>().build()
        WorkManager.getInstance(context)
            .getWorkInfoByIdLiveData(request.id)
            .observe(context as ComponentActivity, Observer { workInfo: WorkInfo? ->
                if (workInfo != null) {
                    val progress = workInfo.progress
                    val value = progress.getInt(Constants.PROGRESS, 0)
                    // Do something with progress information
                }
            })
    }

    private fun setPeriodicWorkRequest(context: Context) {
        // Create a periodic work request with 30 mins as repeat interval
        val repeatInterval = 30
        val periodicWorkRequest = PeriodicWorkRequestBuilder<DownloadWorker>(30, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(context).enqueue(periodicWorkRequest)
    }

    private fun getWorkerConstraints(): Constraints {
        return Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .setRequiresDeviceIdle(false)
            .setRequiresStorageNotLow(false)
            .build()
    }

    // Reporting Worker Progress
    class ProgressWorker(context: Context, parameters: WorkerParameters) :
        CoroutineWorker(context, parameters) {
        override suspend fun doWork(): Result {
            setProgress(workDataOf(Constants.PROGRESS to 25))
            //...
            setProgress(workDataOf(Constants.PROGRESS to 50))
            //...
            return Result.success()
        }
    }
}

@Composable
fun MainScreen(jobState: String, onStartClicked: ()-> Unit) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = jobState)
            Button(onClick = {
                onStartClicked()
            }) {
                Text(text = "Start")
            }
        }
    }
}

/*
class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        val backupWorkRequest = PeriodicWorkRequestBuilder<BackupWorker>(8, TimeUnit.HOURS)
                                        .build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
                "BackupWork",
                ExistingPeriodicWorkPolicy.KEEP,
                backupWorkRequest)

        val workManager = WorkManager.getInstance(applicationContext)
        workManager.getWorkInfosForUniqueWorkLiveData("BackupWork")
    }
}
*/

/*
val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
        .setInitialDelay(10, TimeUnit.MINUTES)
        .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS)
        .setInputData(workDataOf(KEY_IMAGE_URI to imageUriString))
        .setConstraints(constraints)
        .addTag("cleanup")
        .build()
val uploadWorkRequest = PeriodicWorkRequestBuilder<UploadWorker>(1, TimeUnit.HOURS)
        .setConstraints(constraints)
        .build()

 */

/*
WorkManager.getInstance(applicationContext).getWorkInfosByTag("Sync")
WorkManager.getInstance(applicationContext).getWorkInfosForUniqueWork("MyUniqueName")
WorkManager.getInstance(applicationContext).getWorkInfoById(uploadRequest.id)
WorkManager.getInstance(applicationContext).getWorkInfosByTag(TAG_SAVE)
WorkManager.getInstance(applicationContext).getWorkInfosByTagLiveData(TAG_SAVE)

WorkManager.getInstance(applicationContext).cancelAllWork()
*/

//Source: developer.android.com/topic/libraries/architecture/workmanager/how-to/cancel-stop-work
/*
val saveImageWorkRequest = OneTimeWorkRequestBuilder<SaveImageWorker>()
        .addTag(TAG_SAVE)
        .build()

WorkManager.getInstance(applicationContext).cancelWorkById(saveImageWorkRequest.id)
WorkManager.getInstance(applicationContext).cancelAllWorkByTag(TAG_SAVE) */