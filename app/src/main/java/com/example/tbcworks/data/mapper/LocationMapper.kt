package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.model.LocationResponseDto
import com.example.tbcworks.domain.model.Location

fun Location.toData() : LocationResponseDto{
    return LocationResponseDto(
        id = id,
        title = title,
        description = description,
        latitude = latitude,
        longitude = longitude,
        imageUrl = imageUrl
    )
}

fun LocationResponseDto.toDomain() : Location{
    return Location(
        id = id,
        title = title,
        description = description,
        latitude = latitude,
        longitude = longitude,
        imageUrl = imageUrl
    )
}