package com.example.postbook.activities.principalactivities

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.postbook.R
import com.example.postbook.postclassmodels.PostClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class PostBookMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_book_activity_main)
        DownloadFilesTask().execute()
    }

    @SuppressLint("StaticFieldLeak")
    inner class DownloadFilesTask : AsyncTask<URL?, Int?, Long>() {

        private lateinit var postArray: ArrayList<PostClass>

        override fun doInBackground(vararg params: URL?): Long {
            val url = URL("https", "jsonplaceholder.typicode.com", "posts")
            val urlConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection

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
            return 0
        }
    }
}
