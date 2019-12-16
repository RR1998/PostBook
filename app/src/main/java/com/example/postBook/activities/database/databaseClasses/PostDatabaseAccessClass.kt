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
        DatabaseAccessConstantsObjects.databaseName, factory,
        DatabaseAccessConstantsObjects.databaseVersion
    ) {

    override fun onCreate(db: SQLiteDatabase?) {
        val postsTable = DatabaseAccessConstantsObjects.createPostTableQuery
        db?.execSQL(postsTable)
        val commentsTable = DatabaseAccessConstantsObjects.createCommentTableQuery
        db?.execSQL(commentsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun addPosts(posts: MutableList<PostClass>) {

        val db: SQLiteDatabase? = this.writableDatabase

        posts.forEach {
            val values = ContentValues()
            values.put(DatabaseAccessConstantsObjects.tableId, it.id)
            values.put(DatabaseAccessConstantsObjects.userId, it.userId)
            values.put(DatabaseAccessConstantsObjects.tableTittle, it.title)
            values.put(DatabaseAccessConstantsObjects.tableBody, it.body)
            db?.insert(DatabaseAccessConstantsObjects.postTableName, null, values)
        }
        db?.close()
    }

    fun addComments(comments: MutableList<PostCommentClass>) {
        val db: SQLiteDatabase? = this.writableDatabase

        comments.forEach {

            val values = ContentValues()

            values.put(DatabaseAccessConstantsObjects.tableId, it.id)
            values.put(DatabaseAccessConstantsObjects.postId, it.postId)
            values.put(DatabaseAccessConstantsObjects.name, it.name)
            values.put(DatabaseAccessConstantsObjects.email, it.email)
            values.put(DatabaseAccessConstantsObjects.tableBody, it.body)
            db?.insert(DatabaseAccessConstantsObjects.commentsTableName, null, values)
        }
        db?.close()
    }
}