package com.example.tbcworks.domain.validation

interface Validator<T> {
    fun validate(value: T): ValidationResult
}