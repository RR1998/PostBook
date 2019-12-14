package com.example.postbook

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection


object DownloadFilesTask : AsyncTask<URL?, Int?, Long>() {

    override fun doInBackground(vararg params: URL?): Long {

        val urlConnection: HttpsURLConnection = params[0]?.openConnection() as HttpsURLConnection

        urlConnection.requestMethod = "GET"
        urlConnection.readTimeout = 10000
        urlConnection.connectTimeout = 10000
        urlConnection.connect()

        val inputStream = urlConnection.inputStream

        val stringBuilder = StringBuilder()

        val stringJsonPackage: MutableList<String> = mutableListOf()

        var line: String?

        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        line = bufferedReader.readLine()
        stringJsonPackage.add(line)
        while (line != null) {
            stringBuilder.append(line)
            line = bufferedReader.readLine()
            if(line == null){
                break
            }
            stringJsonPackage.add(line)
        }
        
        bufferedReader.close()
        return 0
    }
}