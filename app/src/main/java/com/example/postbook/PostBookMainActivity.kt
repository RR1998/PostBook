package com.example.postbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.net.URL

class PostBookMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_book_activity_main)
        val url = URL("https", "jsonplaceholder.typicode.com", "posts")
        DownloadFilesTask.execute(url)
    }
}
