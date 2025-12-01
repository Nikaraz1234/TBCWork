package com.example.tbcworks.presentation.screens.dashboard.mapper

import com.example.tbcworks.domain.model.GetUser
import com.example.tbcworks.presentation.screens.dashboard.UserModel

fun GetUser.toPresentation() = UserModel(
    id = this.id,
    email = this.email,
    firstName = this.firstName,
    lastName = this.lastName
)
