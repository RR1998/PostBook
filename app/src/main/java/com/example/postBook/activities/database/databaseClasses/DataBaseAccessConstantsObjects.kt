package com.example.postBook.activities.database.databaseClasses

/**
 * Object that contains the constants used to create the tables query and get set rows properties
 */

object DataBaseAccessConstantsObjects {
    const val updateTittle = "dissentiet. At duo zril molestie, pri ne consulatu disputando. Vel ut facer phaedrum "
    const val updateBody = "Lorem ipsum dolor sit amet, pri elitr doctus equidem in, ius te regione deleniti instructior, duo mollis adolescens ne, id has tritani volumus. E"
    const val createPostTableQuery =
        "CREATE TABLE posts(_id INTEGER PRIMARY KEY, userId INTEGER, title TEXT, body TEXT)"
    const val createCommentTableQuery  =
        "CREATE TABLE comments(_id INTEGER PRIMARY KEY, postId INTEGER, name TEXT,email TEXT, body TEXT, FOREIGN KEY(postId) REFERENCES posts(_id))"
    const val tableId = "_id"
    const val postTableName = "posts"
    const val commentsTableName = "comments"
    const val userId = "userId"
    const val postId = "postId"
    const val name = "name"
    const val email = "email"
    const val tableTittle = "title"
    const val tableBody = "body"
    const val databaseVersion = 1
    const val databaseName = "post.db"
}