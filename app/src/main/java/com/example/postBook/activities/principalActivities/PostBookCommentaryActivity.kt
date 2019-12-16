package com.example.postBook.activities.principalActivities

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.postBook.R
import com.example.postBook.activities.adapter.CommentBundleObject
import com.example.postBook.activities.adapter.RecyclerCommentsAdapter
import com.example.postBook.activities.database.databaseClasses.PostDatabaseAccessClass
import com.example.postBook.postClassModels.PostCommentClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * PostBookCommentaryActivity gets the JSON objects and insert it into an array of comments
 * it also callsPostDataBaseAccessClass to insert the array os comments to display it in a recycler
 * view
 */

class PostBookCommentaryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private val postAdapter: RecyclerCommentsAdapter = RecyclerCommentsAdapter()

    var id: Long? = 0

    var commentsArray: MutableList<PostCommentClass> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_book_comment_activity)
        val bundleReceived = intent.extras
        val userId = findViewById<TextView>(R.id.user_id)
        val commentTittle = findViewById<TextView>(R.id.comment_title)
        val commentContent = findViewById<TextView>(R.id.comment_content)
        userId.text = bundleReceived?.getLong(CommentBundleObject.userId).toString()
        commentTittle.text = bundleReceived?.getString(CommentBundleObject.commentTittle)
        commentContent.text = bundleReceived?.getString(CommentBundleObject.commentBody)
        commentsArray = DownloadFilesTask().execute().get()
        setUpRecyclerView()
    }

    /**
     * DownloadFilesTask executes an AsyncTask that gets the JSON from the web it also insert it into
     * an array of comments and calls the DataBaseAccessClass
     */

    @SuppressLint("StaticFieldLeak")
    inner class DownloadFilesTask : AsyncTask<URL?, Int?, MutableList<PostCommentClass>>() {

        override fun doInBackground(vararg params: URL?): MutableList<PostCommentClass> {

            lateinit var auxArray: MutableList<PostCommentClass>

            val bundleReceived = intent.extras

            id = bundleReceived?.getLong(CommentBundleObject.tittleId)

            val url = URL(
                PostBookConstantsObject.protocol,
                PostBookConstantsObject.UrlHost,
                PostBookConstantsObject.commentUrlFile + (id.toString())
            )

            val urlConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection

            urlConnection.requestMethod = PostBookConstantsObject.methodGet
            urlConnection.readTimeout = PostBookConstantsObject.readTimeOut
            urlConnection.connectTimeout = PostBookConstantsObject.connectTimeOut
            urlConnection.connect()

            val inputStream: InputStream = urlConnection.inputStream

            var line: String?

            val bufferedReader = BufferedReader(InputStreamReader(inputStream))

            var stringAux = ""

            val postListType: Type = object : TypeToken<MutableList<PostCommentClass?>?>() {}.type

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
            auxArray = jsonConverter.fromJson(stringAux, postListType)
            auxArray.forEach {
                if (it.postId == id) {
                    commentsArray.add(it)
                }
            }
            PostDatabaseAccessClass(
                this@PostBookCommentaryActivity,
                null
            ).addComments(commentsArray)
            return commentsArray
        }
    }

    /**
     * seUpRecyclerView calls RecyclerCommentsAdapter and its creates the View to inflate
     * the commentary section
     */

    private fun setUpRecyclerView() {
        recyclerView = findViewById(R.id.posts_views)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        postAdapter.recyclerAdapter(commentsArray, this)
        recyclerView.adapter = postAdapter
    }
}