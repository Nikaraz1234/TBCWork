package com.example.mycomposeapp.data.mapper

import com.example.mycomposeapp.data.dto.PostDto
import com.example.mycomposeapp.domain.model.Post

fun PostDto.toDomain(): Post {
    return Post(
        id = id,
        avatar = avatar,
        postDate = postDate,
        firstName = firstName,
        lastName= lastName,
        images = images,
        commentsCount = commentsCount,
        likesCount = likesCount,
        postDesc = postDesc,
        canComment = canComment,
        canPostPhoto = canPostPhoto
    )
}