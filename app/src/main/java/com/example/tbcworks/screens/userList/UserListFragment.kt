package com.example.tbcworks.screens.userList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.R
import com.example.tbcworks.databinding.FragmentUserListBinding
import com.example.tbcworks.BaseFragment
import com.example.tbcworks.helpers.SnackbarHelper
import com.example.tbcworks.userAdapter.UserAdapter

class UserListFragment : BaseFragment<FragmentUserListBinding>() {
    private lateinit var adapter: UserAdapter

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserListBinding {
        return FragmentUserListBinding.inflate(inflater, container, false)
    }

    override fun bind() {
        listeners()
        setUpAdapter()
    }
    private fun setUpAdapter() = with(binding){

        adapter = UserAdapter(emptyList()) { user ->
            if (user != null) {
                val bundle = Bundle().apply {
                    putString("email", user.email)
                }
                findNavController().navigate(R.id.action_userListFragment_to_updateUserFragment2, bundle)

            } else {
                SnackbarHelper.show(binding.root, "Selected user is invalid")
            }
        }
        rvUsers.layoutManager = LinearLayoutManager(requireContext())
        rvUsers.adapter = adapter

        userViewModel.users.observe(viewLifecycleOwner) { userList ->
            adapter.updateUsers(userList)
        }

    }
    override fun listeners() {
        binding.btnBack.setOnClickListener {
            goBack()
        }
    }

    private fun goBack(){
        findNavController().popBackStack()
    }



}