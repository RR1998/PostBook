package com.example.postBook.activities.database.databaseClasses

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.postBook.postClassModels.PostClass
import com.example.postBook.postClassModels.PostCommentClass

/**
 * PostDataBaseAccessClass create the database and its tables
 */

class PostDataBaseAccessClass(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(
        context,
        DataBaseAccessConstantsObjects.databaseName, factory,
        DataBaseAccessConstantsObjects.databaseVersion
    ) {

    override fun onCreate(db: SQLiteDatabase?) {
        val postsTable = DataBaseAccessConstantsObjects.createPostTableQuery
        db?.execSQL(postsTable)
        val commentsTable = DataBaseAccessConstantsObjects.createCommentTableQuery
        db?.execSQL(commentsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun updatePosts() {
        val random = (0..100).random()
        val random2 = (0..100).random()
        val post = PostClass()
        post.id = random.toLong()
        post.userId = random2.toLong()
        post.title = DataBaseAccessConstantsObjects.updateTittle
        post.body = DataBaseAccessConstantsObjects.updateBody
    }

    /**
     *add functions make an insert query to the database in its tables
     */

    fun addPosts(posts: MutableList<PostClass>) {

        val db: SQLiteDatabase? = this.writableDatabase

        posts.forEach {
            val values = ContentValues()
            values.put(DataBaseAccessConstantsObjects.tableId, it.id)
            values.put(DataBaseAccessConstantsObjects.userId, it.userId)
            values.put(DataBaseAccessConstantsObjects.tableTittle, it.title)
            values.put(DataBaseAccessConstantsObjects.tableBody, it.body)
            db?.insert(DataBaseAccessConstantsObjects.postTableName, null, values)
        }
        db?.close()
    }

    fun addComments(comments: MutableList<PostCommentClass>) {
        val db: SQLiteDatabase? = this.writableDatabase

        comments.forEach {

            val values = ContentValues()

            values.put(DataBaseAccessConstantsObjects.tableId, it.id)
            values.put(DataBaseAccessConstantsObjects.postId, it.postId)
            values.put(DataBaseAccessConstantsObjects.name, it.name)
            values.put(DataBaseAccessConstantsObjects.email, it.email)
            values.put(DataBaseAccessConstantsObjects.tableBody, it.body)
            db?.insert(DataBaseAccessConstantsObjects.commentsTableName, null, values)
        }
        db?.close()
    }
}