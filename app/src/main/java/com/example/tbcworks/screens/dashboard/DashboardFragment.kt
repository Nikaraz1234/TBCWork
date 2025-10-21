package com.example.tbcworks.screens.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.R
import com.example.tbcworks.databinding.FragmentMainBinding
import com.example.tbcworks.BaseFragment
import com.example.tbcworks.helpers.SnackbarHelper
import com.example.tbcworks.screens.userList.UserListFragmentDirections

class DashboardFragment : BaseFragment<FragmentMainBinding>() {
    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainBinding.inflate(inflater, container, false)


    override fun listeners() = with(binding) {
        btnAddUser.setOnClickListener {
            addUser()
        }
        btnUpdateUser.setOnClickListener {
            updateUser()
        }
        btnUserList.setOnClickListener {
            showUserList()
        }
    }
    private fun showUserList() = with(binding){
        val users = userViewModel.users.value.orEmpty()
        if (users.isEmpty()) {
            SnackbarHelper.show(root, getString(R.string.no_active_users))
            return@with
        }
        findNavController().navigate(R.id.action_mainFragment2_to_userListFragment)
    }

    override fun bind(){
        listeners()
        setUpCounters()
    }

    private fun setUpCounters() = with(binding) {
        userViewModel.users.observe(viewLifecycleOwner) { users ->
            tvActiveUsers.text = getString(
                R.string.active_users_with_placeholder,
                users.size
            )
        }
        userViewModel.deletedUsers.observe(viewLifecycleOwner) { deleted ->
            tvDeletedUsers.text = getString(
                R.string.deleted_users_with_placeholder,
                deleted
            )
        }
    }

    private fun updateUser() = with(binding) {
        val users = userViewModel.users.value.orEmpty()
        if (users.isEmpty()) {
            SnackbarHelper.show(root, getString(R.string.no_active_users))
            return@with
        }else{
            val randomUser = users.random()
            val bundle = Bundle().apply {
                putString("email", randomUser.email)
            }
            findNavController().navigate(R.id.action_mainFragment2_to_updateUserFragment2, bundle)
        }

    }

    private fun addUser() {
        findNavController().navigate(R.id.action_mainFragment2_to_addUserFragment)
    }

}