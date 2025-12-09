package com.example.tbcworks.presentation.screens.mapper

import com.example.tbcworks.domain.model.GetPost
import com.example.tbcworks.presentation.screens.model.PostModel

fun GetPost.toPresentation() : PostModel{
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