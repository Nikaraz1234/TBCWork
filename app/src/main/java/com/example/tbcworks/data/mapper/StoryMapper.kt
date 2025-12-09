package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.local.entity.StoryEntity
import com.example.tbcworks.data.model.StoryDto
import com.example.tbcworks.domain.model.GetStory

fun StoryDto.toEntity(): StoryEntity {
    return StoryEntity(
        title = this.title,
        cover = this.cover
    )
}

fun StoryDto.toDomain(): GetStory {
    return GetStory(
        title = this.title,
        cover = this.cover
    )
}
fun StoryEntity.toDomain() : GetStory {
    return GetStory(
        title = this.title,
        cover = this.cover
    )
}