package com.example.postBook.activities.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.postBook.R
import com.example.postBook.postClassModels.PostClass

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

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

        fun bind(post: PostClass, context: Context) {
            userId.text = post.userId.toString()
            commentTittle.text = post.title
            commentContent.text = post.body
            itemView.setOnClickListener {
                Toast.makeText(
                    context,
                    post.userId.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}