package com.example.mycomposeapp.ui.screen.feed.mapper

import com.example.mycomposeapp.domain.model.Story
import com.example.mycomposeapp.ui.screen.feed.model.StoryModel

fun Story.toPresentation() : StoryModel {
    return StoryModel(
        id = this.id,
        title = this.title,
        cover = this.cover
    )
}