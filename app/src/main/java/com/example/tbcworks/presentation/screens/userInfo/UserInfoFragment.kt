package com.example.tbcworks.presentation.screens.userInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tbcworks.R
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.databinding.FragmentUserInfoBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extensions.SnackBarHelper.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserInfoFragment : BaseFragment<FragmentUserInfoBinding>() {

    private val viewModel: UserInfoViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserInfoBinding {
        return FragmentUserInfoBinding.inflate(inflater, container, false)
    }

    override fun bind() {
        observeSideEffects()
    }

    override fun listeners() = with(binding) {
        btnSave.setOnClickListener {
            saveUser()
        }
        btnRead.setOnClickListener {
            viewModel.onEvent(UserInfoEvent.ReadUser)
        }
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun saveUser() = with(binding){
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val email = etEmail.text.toString().trim()

        if (viewModel.validateInput(firstName, lastName, email)) {
            viewModel.onEvent(UserInfoEvent.SaveUser(firstName, lastName, email))
        } else {
            root.showSnackBar(INPUT_ERROR_MESSAGE)
            return@with
        }

        etFirstName.setText("")
        etLastName.setText("")
        etEmail.setText("")

    }

    private fun observeSideEffects() = with(binding){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.sideEffect.collect { sideEffect ->
                    when(sideEffect){
                        is UserInfoSideEffect.ShowUser -> {
                            tvFirstName.text = getString(R.string.label_firstname, sideEffect.firstName)
                            tvLastName.text = getString(R.string.label_lastname, sideEffect.lastName)
                            tvEmail.text = getString(R.string.label_email, sideEffect.email)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val INPUT_ERROR_MESSAGE = "Invalid inputs"
    }

}