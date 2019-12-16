package com.example.postBook.activities.database.databaseClasses

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.postBook.postClassModels.PostClass
import com.example.postBook.postClassModels.PostCommentClass

class PostDatabaseAccessClass(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME, factory,
        DATABASE_VERSION
    ) {

    override fun onCreate(db: SQLiteDatabase?) {
        val postsTable = ("CREATE TABLE posts(_id INTEGER PRIMARY KEY, userId INTEGER, title TEXT, body TEXT)")
        db?.execSQL(postsTable)
        val commentsTable = ("CREATE TABLE comments(_id INTEGER PRIMARY KEY, postId INTEGER, name TEXT,email TEXT, body TEXT, FOREIGN KEY(postId) REFERENCES posts(_id))")
        db?.execSQL(commentsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun addPosts(posts: MutableList<PostClass>) {

        val db:SQLiteDatabase? = this.writableDatabase

        posts.forEach {

            val values = ContentValues()

            values.put("_id", it.id)
            values.put("userId", it.userId)
            values.put("title", it.title)
            values.put("body", it.body)
            db?.insert("posts", null, values)
        }
        db?.close()
    }

    fun addComments(comments: MutableList<PostCommentClass>){
        val db:SQLiteDatabase? = this.writableDatabase

        comments.forEach {

            val values = ContentValues()

            values.put("_id", it.id)
            values.put("postId", it.postId)
            values.put("name", it.name)
            values.put("email", it.email)
            values.put("body", it.body)
            db?.insert("posts", null, values)
        }
        db?.close()
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "post.db"
    }
}