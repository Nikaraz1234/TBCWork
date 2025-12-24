package com.example.tbcworks.presentation.screen.register

import com.example.tbcworks.domain.usecase.auth.SignUpUseCase
import com.example.tbcworks.domain.usecase.validation.ValidateDepartmentSelectedUseCase
import com.example.tbcworks.domain.usecase.validation.ValidateEmailFormatUseCase
import com.example.tbcworks.domain.usecase.validation.ValidateNotEmptyUseCase
import com.example.tbcworks.domain.usecase.validation.ValidatePasswordLengthUseCase
import com.example.tbcworks.domain.usecase.validation.ValidatePasswordsMatchUseCase
import com.example.tbcworks.domain.usecase.validation.ValidateTermsAcceptedUseCase
import com.example.tbcworks.domain.validation.ValidationResult
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.presentation.screen.register.mapper.toDomain
import com.example.tbcworks.presentation.screen.register.model.SignUpModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val validateNotEmpty: ValidateNotEmptyUseCase,
    private val validateEmailFormat: ValidateEmailFormatUseCase,
    private val validatePasswordLength: ValidatePasswordLengthUseCase,
    private val validatePasswordsMatch: ValidatePasswordsMatchUseCase,
    private val validateTermsAccepted: ValidateTermsAcceptedUseCase,
    private val validateDepartmentSelected: ValidateDepartmentSelectedUseCase
) : BaseViewModel<SignUpContract.SignUpState, SignUpContract.SignUpSideEffect, SignUpContract.SignUpEvent>(
    SignUpContract.SignUpState()
) {

    fun onEvent(event: SignUpContract.SignUpEvent) {
        when (event) {
            is SignUpContract.SignUpEvent.Submit -> validateAndSignUp(event.model)
            SignUpContract.SignUpEvent.SignInClicked -> sendSideEffect(SignUpContract.SignUpSideEffect.NavigateToSignIn)
        }
    }

    private fun validateAndSignUp(model: SignUpModel) {
        // validate first name
        val firstNameResult = validateNotEmpty.validate(model.firstName)
        if (firstNameResult is ValidationResult.Error) {
            sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage(firstNameResult.message))
            setState { copy(errors = mapOf(SignUpContract.SignUpState.Field.FIRST_NAME to firstNameResult.message)) }
            return
        }

        // validate last name
        val lastNameResult = validateNotEmpty.validate(model.lastName)
        if (lastNameResult is ValidationResult.Error) {
            sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage(lastNameResult.message))
            setState { copy(errors = mapOf(SignUpContract.SignUpState.Field.LAST_NAME to lastNameResult.message)) }
            return
        }

        // validate email
        val emailResult = validateEmailFormat.validate(model.email)
        if (emailResult is ValidationResult.Error) {
            sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage(emailResult.message))
            setState { copy(errors = mapOf(SignUpContract.SignUpState.Field.EMAIL to emailResult.message)) }
            return
        }

        // validate phone
        val phoneResult = validateNotEmpty.validate(model.phoneNumber)
        if (phoneResult is ValidationResult.Error) {
            sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage(phoneResult.message))
            setState { copy(errors = mapOf(SignUpContract.SignUpState.Field.PHONE to phoneResult.message)) }
            return
        }

        // validate OTP
        val otpResult = validateNotEmpty.validate(model.otpCode)
        if (otpResult is ValidationResult.Error) {
            sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage(otpResult.message))
            setState { copy(errors = mapOf(SignUpContract.SignUpState.Field.OTP to otpResult.message)) }
            return
        }

        // validate department
        val deptResult = validateDepartmentSelected.validate(model.department)
        if (deptResult is ValidationResult.Error) {
            sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage(deptResult.message))
            setState { copy(errors = mapOf(SignUpContract.SignUpState.Field.DEPARTMENT to deptResult.message)) }
            return
        }

        // validate password
        val passwordResult = validatePasswordLength.validate(model.password)
        if (passwordResult is ValidationResult.Error) {
            sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage(passwordResult.message))
            setState { copy(errors = mapOf(SignUpContract.SignUpState.Field.PASSWORD to passwordResult.message)) }
            return
        }

        // validate password match
        val confirmResult = validatePasswordsMatch.validate(model.password, model.confirmPassword)
        if (confirmResult is ValidationResult.Error) {
            sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage(confirmResult.message))
            setState { copy(errors = mapOf(SignUpContract.SignUpState.Field.CONFIRM_PASSWORD to confirmResult.message)) }
            return
        }

        // validate terms acceptance
        val policyResult = validateTermsAccepted.validate(model.isPolicyAccepted)
        if (policyResult is ValidationResult.Error) {
            sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage(policyResult.message))
            setState { copy(errors = mapOf(SignUpContract.SignUpState.Field.POLICY to policyResult.message)) }
            return
        }

        // all validations passed → proceed with signup
        setState { copy(isLoading = true) }
        handleResponse(
            apiCall = { signUpUseCase.invoke(model.toDomain()) },
            onSuccess = { sendSideEffect(SignUpContract.SignUpSideEffect.NavigateToHome) },
            onError = { sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage(it)) },
            onLoading = { setState { copy(isLoading = true) } }
        )
    }


}
