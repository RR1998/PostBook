package com.example.postBook.activities.principalActivities

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
            val url = URL(PROTOCOL, POST_URL_HOST, POST_URL_FILE)
            val urlConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection

            urlConnection.requestMethod = REQUEST_METHOD_GET
            urlConnection.readTimeout = READ_TIME_OUT
            urlConnection.connectTimeout = CONNECT_TIME_OUT
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
            //PostDatabaseAccessClass(this@PostBookMainActivity, null).addName(postArray[0])
            return 0
        }
    }
    companion object{
        const val READ_TIME_OUT = 10000
        const val CONNECT_TIME_OUT = 10000
        const val REQUEST_METHOD_GET = "GET"
        const val PROTOCOL = "https"
        const val POST_URL_HOST = "jsonplaceholder.typicode.com"
        const val POST_URL_FILE = "posts"
    }
}
