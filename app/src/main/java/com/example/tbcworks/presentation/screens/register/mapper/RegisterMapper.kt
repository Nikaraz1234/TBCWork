package com.example.tbcworks.presentation.screens.register.mapper

import com.example.tbcworks.domain.model.GetRegisterResponse
import com.example.tbcworks.presentation.screens.register.RegisterModel

fun GetRegisterResponse.toPresentation() = RegisterModel(
    id = this.id,
    token = this.token
)