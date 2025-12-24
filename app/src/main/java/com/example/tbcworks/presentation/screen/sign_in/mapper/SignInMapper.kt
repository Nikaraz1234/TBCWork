package com.example.tbcworks.presentation.screen.sign_in.mapper

import com.example.tbcworks.domain.model.auth.SignIn
import com.example.tbcworks.presentation.screen.sign_in.model.SignInModel

fun SignInModel.toDomain() = SignIn(
    email = this.email,
    password = this.password
)