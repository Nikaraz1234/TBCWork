package com.example.tbcworks.presentation.screens.login.mapper

import com.example.tbcworks.domain.model.GetLoginResponse
import com.example.tbcworks.presentation.screens.login.LoginModel

fun GetLoginResponse.toPresentation() = LoginModel(
    token = this.token
)