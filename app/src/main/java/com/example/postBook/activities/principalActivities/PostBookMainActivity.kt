package com.example.postBook.activities.principalActivities

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.postBook.R
import com.example.postBook.activities.adapter.RecyclerPostAdapter
import com.example.postBook.postClassModels.PostClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class PostBookMainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    private val postAdapter: RecyclerPostAdapter = RecyclerPostAdapter()

    var postArray: MutableList<PostClass> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_book_activity_main)
        postArray = DownloadFilesTask().execute().get()
        setUpRecyclerView()
    }

    @SuppressLint("StaticFieldLeak")
    inner class DownloadFilesTask : AsyncTask<URL?, Int?, MutableList<PostClass>>() {

        override fun doInBackground(vararg params: URL?): MutableList<PostClass> {

            val url = URL(
                PostBookConstantsObject.protocol,
                PostBookConstantsObject.UrlHost, PostBookConstantsObject.postUrlFile
            )
            val urlConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection

            urlConnection.requestMethod = PostBookConstantsObject.methodGet
            urlConnection.readTimeout = PostBookConstantsObject.readTimeOut
            urlConnection.connectTimeout = PostBookConstantsObject.connectTimeOut
            urlConnection.connect()

            val inputStream:InputStream = urlConnection.inputStream

            var line: String?

            val bufferedReader = BufferedReader(InputStreamReader(inputStream))

            var stringAux = ""

            val postListType = object : TypeToken<MutableList<PostClass?>?>() {}.type

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
            return postArray
        }
    }

    private fun setUpRecyclerView() {

        recyclerView = findViewById(R.id.posts_views)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        postAdapter.recyclerAdapter(postArray, this)
        recyclerView.adapter = postAdapter

    }
}
