package com.example.mycomposeapp.domain.usecase.validation

import javax.inject.Inject

class EmptyFieldUseCase @Inject constructor(){
    operator fun invoke(value: String?): Boolean {
        return !value.isNullOrBlank()
    }
}