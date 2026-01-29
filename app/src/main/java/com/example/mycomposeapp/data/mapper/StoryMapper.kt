package com.example.mycomposeapp.data.mapper

import com.example.mycomposeapp.data.dto.StoryDto
import com.example.mycomposeapp.domain.model.Story

fun StoryDto.toDomain() : Story {
        return Story(
            id = this.id,
            title = this.title,
            cover = this.cover
        )
}