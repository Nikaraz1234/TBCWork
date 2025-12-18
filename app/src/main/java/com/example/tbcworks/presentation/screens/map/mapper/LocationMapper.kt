package com.example.tbcworks.presentation.screens.map.mapper

import com.example.tbcworks.domain.model.Location
import com.example.tbcworks.presentation.screens.map.model.LocationModel

fun Location.toPresentation() : LocationModel{
    return LocationModel(
        id = id,
        title = title,
        description = description,
        latitude = latitude,
        longitude = longitude,
        imageUrl = imageUrl
    )
}

fun LocationModel.toDomain() : Location{
    return Location(
        id = id,
        title = title,
        description = description,
        latitude = latitude,
        longitude = longitude,
        imageUrl = imageUrl
    )
}