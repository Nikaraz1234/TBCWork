package com.example.tbcworks.presentation.screens.user_list

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extensions.collectFlow
import com.example.tbcworks.presentation.extensions.collectStateFlow
import com.example.tbcworks.databinding.FragmentUserListBinding
import com.example.tbcworks.presentation.screens.user_list.adapter.UserListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : BaseFragment<FragmentUserListBinding>(
    FragmentUserListBinding::inflate
) {

    private val viewModel: UserListViewModel by viewModels()
    private val adapter: UserListAdapter by lazy { UserListAdapter() }

    override fun bind() {
        setupRecyclerView()
        viewModel.onEvent(UserListEvent.LoadUsers)
        observeState()
        observeSideEffects()

    }

    private fun setupRecyclerView() = with(binding){
        rvUsers.layoutManager = LinearLayoutManager(requireContext())
        rvUsers.adapter = adapter
    }

    private fun observeState() {
        viewLifecycleOwner.collectStateFlow(viewModel.uiState) { state ->
            adapter.submitList(state.users)
            binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.collectFlow(viewModel.sideEffect) { sideEffect ->
            when (sideEffect) {
                is UserListSideEffect.ShowError ->
                    Toast.makeText(requireContext(), sideEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
