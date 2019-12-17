package com.example.postBook.activities.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.postBook.R
import com.example.postBook.postClassModels.PostCommentClass

/**
 * RecyclerCommentAdapter initialize and create the view that inflates the commentary feed
 */

class RecyclerCommentsAdapter : RecyclerView.Adapter<RecyclerCommentsAdapter.ViewHolder>() {

    private var posts: MutableList<PostCommentClass> = ArrayList()

    private lateinit var context: Context

    fun recyclerAdapter(posts: MutableList<PostCommentClass>, context: Context) {
        this.posts = posts
        this.context = context
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = posts[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_comment_list, parent, false))
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name = view.findViewById(R.id.user_id) as TextView
        private val email = view.findViewById(R.id.comment_email) as TextView
        private val commentContent = view.findViewById(R.id.comment_content_body) as TextView

        fun bind(post: PostCommentClass) {
            name.text = post.name
            email.text = post.email
            commentContent.text = post.body
        }
    }
}