package com.example.tbcworks.presentation.screens.home.mapper

import com.example.tbcworks.domain.model.GetPost
import com.example.tbcworks.presentation.screens.home.model.PostModel

fun GetPost.toPresentation() : PostModel {
    return PostModel(
        avatar = avatar,
        postDate = postDate,
        firstName = firstName,
        lastName = lastName,
        images = images,
        commentsCount = commentsCount,
        likesCount = likesCount,
        postDesc = postDesc,
        canComment = canComment,
        canPostPhoto = canPostPhoto
    )
}