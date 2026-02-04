package com.example.domain.usecase.form

import com.example.domain.Resource
import com.example.domain.model.Form
import com.example.domain.repository.FormRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetFormUseCase @Inject constructor(
    private val repository: FormRepository
) {
    operator fun invoke(): Flow<Resource<Form>> {
        return repository.getForm()
    }
}
