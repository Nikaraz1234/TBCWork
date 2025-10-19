package com.example.tbcworks.fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.MainActivity
import com.example.tbcworks.R
import com.example.tbcworks.helpers.SnackbarHelper
import com.example.tbcworks.User
import com.example.tbcworks.UserManager
import com.example.tbcworks.databinding.FragmentUpdateUserBinding
import kotlin.getValue

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateUserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var userManager : UserManager
    private var _binding: FragmentUpdateUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UpdateUserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdateUserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = (activity as MainActivity).userManager
        setup()
    }

    private fun setup(){
        randomUser()
        listeners()
    }
    private fun listeners() = with(binding){
        btnUpdateUser.setOnClickListener {
            updateUser()
        }
        btnDeleteUser.setOnClickListener {
            deleteUser()
        }
    }

    private fun deleteUser() = with(binding){
        val email = etEmail.text.toString().trim()
        userManager.deleteUser(email)
        findNavController().popBackStack()
    }

    private fun randomUser() = with(binding){
        val user = userManager.users.random()
        etEmail.setText(user.email)
        etEmail.isEnabled = false
        etFirstName.setText(user.firstName)
        etLastName.setText(user.lastName)
        etAge.setText(user.age.toString())

    }

    private fun updateUser() = with(binding){
        val email = etEmail.text.toString().trim()
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val age = etAge.text.toString().trim()

        if(validateInputs(firstName,lastName,age,email)){
            val user = User(firstName, lastName, age.toInt(), email)
            userManager.updateUser(user)
            findNavController().popBackStack()
        }
    }

    private fun validateInputs(firstName : String, lastName:String, age:String, email:String) : Boolean = with(binding){
        if(firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || email.isEmpty()){
            SnackbarHelper.show(root, getString(R.string.fill_all_info))
            return@with false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.error = getString(R.string.invalid_email)
            return@with false
        }
        return@with true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}