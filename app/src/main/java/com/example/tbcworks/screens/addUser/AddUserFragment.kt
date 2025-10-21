package com.example.tbcworks.screens.addUser

import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.R
import com.example.tbcworks.userModel.User
import com.example.tbcworks.databinding.FragmentAddUserBinding
import com.example.tbcworks.BaseFragment
import com.example.tbcworks.helpers.SnackbarHelper

class AddUserFragment : BaseFragment<FragmentAddUserBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddUserBinding {
        return FragmentAddUserBinding.inflate(inflater, container, false)
    }


    override fun bind() {
        listeners()
    }

    override fun listeners() = with(binding){
        btnAddUser.setOnClickListener{
            addUser()
        }
    }

    private fun addUser()= with(binding){
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val age = etAge.text.toString().trim()
        val email = etEmail.text.toString().trim()

        if(validateInputs(firstName,lastName,age,email)) {
            val user = User(firstName, lastName, age.toInt(), email)
            userViewModel.addUser(user)
            findNavController().popBackStack()
        }
    }

    private fun validateInputs(firstName : String, lastName:String, age:String, email:String) : Boolean = with(binding){
        return if(firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || email.isEmpty()){
            SnackbarHelper.show(root, getString(R.string.fill_all_info))
            false
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.error = getString(R.string.invalid_email)
            false
        }else{
            true
        }

    }
}