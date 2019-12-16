package com.example.postBook.activities.principalActivities

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.postBook.postClassModels.PostClass

class PostDatabaseAccessClass(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME, factory,
        DATABASE_VERSION
    ) {

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun addName(post: PostClass) {

        val db = this.writableDatabase

        val values = ContentValues()

        values.put("_id", post.id)
        values.put("userid", post.userId)
        values.put("title", post.title)
        values.put("body", post.body)
        db.insert("posts", null, values)
        db.close()
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "post.db"
    }
}