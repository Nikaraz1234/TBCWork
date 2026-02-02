package com.example.mycomposeapp.domain.usecase.form

import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Form
import com.example.mycomposeapp.domain.repository.FormRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFormUseCase @Inject constructor(
    private val repository: FormRepository
) {
    operator fun invoke(): Flow<Resource<Form>> {
        return repository.getForm()
    }
}
