package com.example.mycomposeapp.ui.screen.dashboard.mapper

import com.example.mycomposeapp.domain.model.Location
import com.example.mycomposeapp.ui.screen.dashboard.model.LocationModel

fun Location.toPresentation(): LocationModel {
    return LocationModel(
        id = id,
        title = title,
        location = location,
        number = number,
        photo = photo,
        price = price,
        stars = stars
    )
}