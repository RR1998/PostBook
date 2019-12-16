package com.example.postBook.activities.principalActivities

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.postBook.R
import com.example.postBook.activities.adapter.RecyclerCommentsAdapter
import com.example.postBook.postClassModels.PostCommentClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class PostBookCommentaryActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView

    private val postAdapter : RecyclerCommentsAdapter = RecyclerCommentsAdapter()

    var id: Long? = 0

    var commentsArray: MutableList<PostCommentClass> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_book_comment_activity)
        val bundleReceived = intent.extras
        val userId = findViewById<TextView>(R.id.user_id)
        val commentTittle = findViewById<TextView>(R.id.comment_title)
        val commentContent = findViewById<TextView>(R.id.comment_content)
        userId.text = bundleReceived?.getLong("userIdSelected").toString()
        commentTittle.text = bundleReceived?.getString("tittleSelected")
        commentContent.text = bundleReceived?.getString("bodySelected")
        commentsArray = DownloadFilesTask().execute().get()
        setUpRecyclerView()
    }

    @SuppressLint("StaticFieldLeak")
    inner class DownloadFilesTask : AsyncTask<URL?, Int?, MutableList<PostCommentClass>>() {

        override fun doInBackground(vararg params: URL?): MutableList<PostCommentClass> {

            lateinit var auxArray: MutableList<PostCommentClass>

            val bundleReceived = intent.extras

            id = bundleReceived?.getLong("commentSelected")

            val url = URL(
                PROTOCOL,
                POST_URL_HOST,
                POST_URL_FILE+(id.toString())
            )

            val urlConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection

            urlConnection.requestMethod = REQUEST_METHOD_GET
            urlConnection.readTimeout = READ_TIME_OUT
            urlConnection.connectTimeout = CONNECT_TIME_OUT
            urlConnection.connect()

            val inputStream = urlConnection.inputStream

            var line: String?

            val bufferedReader = BufferedReader(InputStreamReader(inputStream))

            var stringAux = ""

            val postListType = object : TypeToken<MutableList<PostCommentClass?>?>() {}.type

            val jsonConverter = Gson()

            line = bufferedReader.readLine()

            Log.v("aaaa", id.toString())
            while (line != null) {
                stringAux = "$stringAux$line"
                line = bufferedReader.readLine()
                if (line == null) {
                    break
                }
            }

            bufferedReader.close()
            auxArray = jsonConverter.fromJson(stringAux, postListType)
            auxArray.forEach {
                if (it.postId == id) {
                    commentsArray.add(it)
                }
            }
            commentsArray.forEach {
                Log.v("aaaa", it.id.toString())
            }
            return commentsArray
        }
    }

    private fun setUpRecyclerView(){
        recyclerView = findViewById(R.id.posts_views)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        postAdapter.recyclerAdapter(commentsArray, this)
        recyclerView.adapter = postAdapter
    }

    companion object {
        const val READ_TIME_OUT = 10000
        const val CONNECT_TIME_OUT = 10000
        const val REQUEST_METHOD_GET = "GET"
        const val PROTOCOL = "https"
        const val POST_URL_HOST = "jsonplaceholder.typicode.com"
        const val POST_URL_FILE = "comments?postId="
    }
}