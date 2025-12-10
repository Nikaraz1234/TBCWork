package com.example.tbcworks.presentation.screens.home.mapper

import com.example.tbcworks.domain.model.GetStory
import com.example.tbcworks.presentation.screens.home.model.StoryModel

fun GetStory.toPresentation(): StoryModel {
    return StoryModel(
        title = this.title,
        cover = this.cover
    )
}