package com.example.mycomposeapp.data.mapper

import com.example.mycomposeapp.data.dto.LocationDto
import com.example.mycomposeapp.domain.model.Location

fun LocationDto.toDomain(): Location {
    return Location(
        id = id,
        title = title,
        location = location,
        number = number,
        photo = photo,
        price = price,
        stars = stars
    )
}