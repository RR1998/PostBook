package com.example.postbook.activities.backgroundactivities

import android.os.AsyncTask
import com.example.postbook.activities.principalactivities.PostDatabaseAccessClass
import com.example.postbook.postclassmodels.PostClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection


object DownloadFilesTask : AsyncTask<URL?, Int?, Long>() {

    lateinit var postArray: ArrayList<PostClass>

    override fun doInBackground(vararg params: URL?): Long {

        val urlConnection: HttpsURLConnection = params[0]?.openConnection() as HttpsURLConnection

        urlConnection.requestMethod = "GET"
        urlConnection.readTimeout = 10000
        urlConnection.connectTimeout = 10000
        urlConnection.connect()

        val inputStream = urlConnection.inputStream

        var line: String?

        val bufferedReader = BufferedReader(InputStreamReader(inputStream))

        var stringAux = ""

        val postListType = object : TypeToken<ArrayList<PostClass?>?>() {}.type

        val jsonConverter = Gson()

        line = bufferedReader.readLine()

        while (line != null) {
            stringAux = "$stringAux$line"
            line = bufferedReader.readLine()
            if (line == null) {
                break
            }
        }

        bufferedReader.close()
        postArray = jsonConverter.fromJson(stringAux, postListType)
        PostDatabaseAccessClass().addName(postArray[0])
        return 0
    }

}