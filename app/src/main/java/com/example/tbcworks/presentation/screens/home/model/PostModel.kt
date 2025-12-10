package com.example.tbcworks.presentation.screens.home.model

data class PostModel(
    val avatar: String,
    val postDate: Long,
    val firstName: String,
    val lastName: String,
    val images: List<String>,
    val commentsCount: Int,
    val likesCount: Int,
    val postDesc: String,
    val canComment: Boolean,
    val canPostPhoto: Boolean
)