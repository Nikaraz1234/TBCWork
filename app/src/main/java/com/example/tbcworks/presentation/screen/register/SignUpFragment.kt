package com.example.tbcworks.presentation.screen.register

import android.R
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.databinding.FragmentSignUpBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.collectStateFlow
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.hideKeyboard
import com.example.tbcworks.presentation.extension.showSnackBar
import com.example.tbcworks.presentation.screen.register.adapter.DigitAdapter
import com.example.tbcworks.presentation.screen.register.model.DigitInput
import com.example.tbcworks.presentation.screen.register.model.SignUpModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::inflate
) {

    private val viewModel: SignUpViewModel by viewModels()

    private val digitAdapter: DigitAdapter by lazy {
        DigitAdapter { otp ->
        }
    }


    override fun bind() {
        observe()
        setupRv()
        setUpDropDown()
    }

    private fun observe(){
        collectStateFlow(viewModel.uiState) { state ->
            binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
            binding.btnCreateAccount.isClickable = !state.isLoading
        }

        // observe side effects
        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                is SignUpContract.SignUpSideEffect.ShowMessage -> binding.root.showSnackBar(effect.message)
                is SignUpContract.SignUpSideEffect.NavigateToHome -> {
                    // navigate to home fragment/activity
                }
                is SignUpContract.SignUpSideEffect.NavigateToSignIn -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun setUpDropDown(){
        val departments = listOf("HR", "Finance", "IT", "Marketing", "Operations")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, departments)
        binding.departmentDropdown.setAdapter(adapter)

        binding.departmentDropdown.setOnClickListener {
            binding.departmentDropdown.showDropDown()
        }
    }

    private fun setupRv() {
        binding.rvOtpCode.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = digitAdapter
            isNestedScrollingEnabled = false
        }

        val initialDigits = List(6) { DigitInput("") }
        digitAdapter.submitList(initialDigits)
    }

    override fun listeners() = with(binding){
        btnCreateAccount.setOnClickListener {
            root.hideKeyboard()

            val otpCode = digitAdapter.currentList.joinToString("") { it.value }
            val model = SignUpModel(
                firstName = etFirstName.text.toString(),
                lastName = etLastName.text.toString(),
                email = etEmail.text.toString(),
                phoneNumber = etPhoneNumber.text.toString(),
                otpCode = otpCode,
                department = departmentDropdown.text.toString(),
                password = etPassword.text.toString(),
                confirmPassword = etConfirmPassword.text.toString(),
                isPolicyAccepted = checkboxPolicy.isChecked
            )
            viewModel.onEvent(SignUpContract.SignUpEvent.Submit(model))
        }

        tvBtnSignIn.setOnClickListener {
            viewModel.onEvent(SignUpContract.SignUpEvent.SignInClicked)
        }
    }
}
