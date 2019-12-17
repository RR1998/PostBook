package com.example.postBook

import android.content.Context
import android.util.Log
import androidx.work.*
import com.example.postBook.activities.database.databaseClasses.PostDataBaseAccessClass
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class UpdateDataBase(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    private var context = appContext
    override fun doWork(): Result {
        Log.v("Successful", "Successful update")
        PostDataBaseAccessClass(context, null).updatePosts()
        WorkManager.getInstance().enqueue(
            PeriodicWorkRequestBuilder<UpdateDataBase>(15L, TimeUnit.MINUTES)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                ).build()
        )
        return Result.success()
    }

    companion object {
        fun start(context: Context) {
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                tag, ExistingPeriodicWorkPolicy.REPLACE,
                PeriodicWorkRequestBuilder<UpdateDataBase>(15L, TimeUnit.MINUTES)
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()
                    ).build()
            )
        }

        private const val tag = "WorkManagerTag"
    }
}