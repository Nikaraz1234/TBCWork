package com.example.tbcworks.presentation.screens.home.mapper

import com.example.tbcworks.domain.model.Location
import com.example.tbcworks.presentation.screens.home.LocationModel

fun Location.toPresentation(): LocationModel {
    return LocationModel(
        location = this.location,
        altitude = this.altitude,
        title = this.title,
        image = this.image,
        stars = this.stars
    )
}