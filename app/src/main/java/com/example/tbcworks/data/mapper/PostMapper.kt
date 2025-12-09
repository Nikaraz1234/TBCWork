package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.local.entity.PostEntity
import com.example.tbcworks.data.model.PostDto
import com.example.tbcworks.domain.model.GetPost

fun PostDto.toEntity(): PostEntity = PostEntity(
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

fun PostEntity.toDomain(): GetPost = GetPost(
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

fun PostDto.toDomain(): GetPost = GetPost(
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