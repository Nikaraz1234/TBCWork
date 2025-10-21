package com.example.tbcworks.screens.updateUser

import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbcworks.R
import com.example.tbcworks.userModel.User
import com.example.tbcworks.databinding.FragmentUpdateUserBinding
import com.example.tbcworks.BaseFragment
import com.example.tbcworks.helpers.SnackbarHelper

class UpdateUserFragment : BaseFragment<FragmentUpdateUserBinding>() {
    private var selectedUser: User? = null


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUpdateUserBinding {
        return FragmentUpdateUserBinding.inflate(inflater, container, false)
    }

    override fun bind() {
        val email = arguments?.getString("email")
        setupListeners()
        findUserByEmail(email)
        fillUserInfo()

    }
    private fun findUserByEmail(email: String?) {
        if (email.isNullOrEmpty()) {
            return
        }
        selectedUser = userViewModel.users.value?.find {
            it.email == email
        }
    }

    private fun setupListeners() = with(binding) {
        btnUpdateUser.setOnClickListener { updateUser() }
        btnDeleteUser.setOnClickListener { deleteUser() }
    }

    private fun deleteUser() = with(binding) {
        val email = etEmail.text.toString().trim()
        userViewModel.deleteUser(email)
        findNavController().popBackStack()
    }


    private fun fillUserInfo() = with(binding) {
        val users = userViewModel.users.value ?: emptyList()
        if (users.isEmpty()) {
            SnackbarHelper.show(root, getString(R.string.no_active_users))
            return@with
        }

        val userToShow = selectedUser ?: users.random()

        etEmail.setText(userToShow.email)
        etEmail.isEnabled = false
        etFirstName.setText(userToShow.firstName)
        etLastName.setText(userToShow.lastName)
        etAge.setText(userToShow.age.toString())
    }


    private fun updateUser() = with(binding) {
        val email = etEmail.text.toString().trim()
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val age = etAge.text.toString().trim()

        if (validateInputs(firstName, lastName, age, email)) {
            val user = User(firstName, lastName, age.toInt(), email)
            userViewModel.updateUser(user)
            findNavController().popBackStack()
        }
    }

    private fun validateInputs(firstName: String, lastName: String, age: String, email: String): Boolean = with(binding) {
        return if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || email.isEmpty()) {
            SnackbarHelper.show(root, getString(R.string.fill_all_info))
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = getString(R.string.invalid_email)
            false
        }else{
            true
        }

    }
}