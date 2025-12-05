package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.model.LocationDto
import com.example.tbcworks.domain.model.Location

fun LocationDto.toDomain(): Location {
    return Location(
        location = location,
        altitude = altitude,
        title = title,
        image = image,
        stars = stars
    )
}