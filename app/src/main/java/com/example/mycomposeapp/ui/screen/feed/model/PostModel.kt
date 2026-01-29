package com.example.mycomposeapp.ui.screen.feed.model

data class PostModel(
    val id: Int,
    val avatar: String?,
    val postDate: String,
    val name: String,
    val images: List<String>,
    val commentsCount: Int,
    val likesCount: Int,
    val postDesc: String?,
    val canComment: Boolean,
    val canPostPhoto: Boolean
)