package com.example.tbcworks.presentation.screens.mapper

import com.example.tbcworks.domain.model.GetStory
import com.example.tbcworks.presentation.screens.model.StoryModel

fun GetStory.toPresentation():  StoryModel{
    return StoryModel(
        title = this.title,
        cover = this.cover
    )
}