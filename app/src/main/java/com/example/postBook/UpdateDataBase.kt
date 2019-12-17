package com.example.postBook

import android.content.Context
import android.util.Log
import androidx.work.*
import com.example.postBook.activities.database.databaseClasses.PostDataBaseAccessClass
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class UpdateDataBase(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    private var context: Context = appContext

    override fun doWork(): Result {
        Log.v("Successful", "Successful update")
        PostDataBaseAccessClass(context, null).updatePosts()
        return Result.success()
    }

    companion object {

        fun start(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val saveRequest =
                PeriodicWorkRequestBuilder<UpdateDataBase>(15L, TimeUnit.MINUTES)
                    .setInitialDelay(1L, TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .build()
            WorkManager.getInstance(context)
                .enqueue(saveRequest)
        }
    }
}