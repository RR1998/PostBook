package com.example.postBook


import android.app.Application

class WorkerApplicationExecute: Application(){
    override fun onCreate() {
        super.onCreate()
        UpdateDataBase.start(this@WorkerApplicationExecute)
    }
}