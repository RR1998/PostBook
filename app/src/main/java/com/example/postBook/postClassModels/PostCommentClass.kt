package com.example.postBook.postClassModels

class PostCommentClass {
    var postId: Long = 0 //this will be use as foreign key
    var id: Long = 0 //This is the primary key
    var name: String = ""
    var email:String = ""
    var body: String = ""
}