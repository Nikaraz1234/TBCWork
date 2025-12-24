package com.example.tbcworks.presentation.screen.register.mapper

import com.example.tbcworks.domain.model.auth.SignUp
import com.example.tbcworks.presentation.screen.register.model.SignUpModel

fun SignUpModel.toDomain(): SignUp {
    return SignUp(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        phoneNumber = this.phoneNumber,
        otpCode = this.otpCode,
        department = this.department,
        password = this.password
    )
}
