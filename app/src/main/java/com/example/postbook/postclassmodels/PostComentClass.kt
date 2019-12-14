package com.example.postbook.postclassmodels

class PostComentClass {
    var postId: Long = 0 //this will be use as foreign key
    var id: Long = 0 //This is the primary key
    var userName: String = ""
    var email:String = ""
    var body: String = ""
}