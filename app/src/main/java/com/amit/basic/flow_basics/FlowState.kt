package com.amit.basic.flow_basics

data class ProfileState(
    val user: User?,
    val postList: List<Post> = emptyList<Post>()
)

data class User(
    val id: Int,
    val name: String
)

data class Post(
    val postId : Int,
    val url: String
)