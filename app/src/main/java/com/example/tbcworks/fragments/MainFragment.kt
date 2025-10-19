package com.example.tbcworks.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.MainActivity
import com.example.tbcworks.R
import com.example.tbcworks.helpers.SnackbarHelper
import com.example.tbcworks.UserManager
import com.example.tbcworks.databinding.FragmentMainBinding



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var userManager : UserManager

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
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = (activity as MainActivity).userManager
        setUp()
    }

    private fun setUp(){
        setUpCounters()
        setUpButtons()
    }

    private fun setUpButtons() =
        with(binding){
            btnAddUser.setOnClickListener {
                addUser()
            }
            btnUpdateUser.setOnClickListener {
                updateUser()
            }
        }



    private fun setUpCounters(){
        with(binding){
            tvActiveUsers.text = getString(R.string.active_users_with_placeholder, userManager.users.size)
            tvDeletedUsers.text = getString(R.string.deleted_users_with_placeholder, userManager.deletedUsers)
        }

    }
    private fun updateUser() = with(binding) {
        if(userManager.users.isEmpty()){
            SnackbarHelper.show(root,getString(R.string.no_active_users))
            return@with
        }
        findNavController().navigate(R.id.action_mainFragment2_to_updateUserFragment2)
    }

    private fun addUser(){
        findNavController().navigate(R.id.action_mainFragment2_to_addUserFragment)
    }

    override fun onResume() {
        super.onResume()
        setUpCounters()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}