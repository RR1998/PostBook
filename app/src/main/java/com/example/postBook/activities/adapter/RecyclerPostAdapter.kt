package com.example.postBook.activities.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.postBook.R
import com.example.postBook.activities.principalActivities.PostBookCommentaryActivity
import com.example.postBook.postClassModels.PostClass

class RecyclerPostAdapter : RecyclerView.Adapter<RecyclerPostAdapter.ViewHolder>() {

    private var posts: MutableList<PostClass> = ArrayList()

    private lateinit var context: Context

    fun recyclerAdapter(posts: MutableList<PostClass>, context: Context) {
        this.posts = posts
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = posts[position]
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_post_list, parent, false))
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val userId = view.findViewById(R.id.user_id) as TextView
        private val commentTittle = view.findViewById(R.id.comment_title) as TextView
        private val commentContent = view.findViewById(R.id.comment_content) as TextView
        private val commentImage = view.findViewById(R.id.image_comment) as ImageView
        fun bind(post: PostClass, context: Context) {
            userId.text = post.userId.toString()
            commentTittle.text = post.title
            commentContent.text = post.body
            commentImage.setImageResource(R.drawable.post_book_comment_icon)
            commentImage.setOnClickListener {
                val detailClassIntent = Intent(
                    context,
                    PostBookCommentaryActivity::class.java
                )
                val bundle = Bundle()
                bundle.putLong(CommentBundleObject.userId, post.userId)
                bundle.putLong(CommentBundleObject.tittleId, post.id)
                bundle.putString(CommentBundleObject.commentTittle, post.title)
                bundle.putString(CommentBundleObject.commentBody, post.body)
                detailClassIntent.putExtras(bundle)
                startActivity(context, detailClassIntent, null)
            }
        }
    }
}