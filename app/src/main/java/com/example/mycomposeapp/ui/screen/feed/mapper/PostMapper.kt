package com.example.mycomposeapp.ui.screen.feed.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mycomposeapp.domain.model.Post
import com.example.mycomposeapp.ui.screen.feed.model.PostModel

fun Post.toPresentation(): PostModel {
    return PostModel(
        id = id,
        avatar = avatar,
        postDate = postDate.toPostDateString(),
        name = "${this.firstName} ${this.lastName}",
        images = images,
        commentsCount = commentsCount,
        likesCount = likesCount,
        postDesc = postDesc,
        canComment = canComment,
        canPostPhoto = canPostPhoto
    )
}