package com.example.tbcworks.presentation.screens.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.presentation.BaseFragment
import com.example.tbcworks.databinding.FragmentDashboardBinding
import kotlinx.coroutines.launch


class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var adapter: DashboardAdapter

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)
    }
    override fun bind() {
        setupRv()
        observe()
        viewModel.onEvent(DashboardEvent.FetchUsers)
    }

    private fun setupRv() = with(binding) {
        adapter = DashboardAdapter()
        rvUsers.layoutManager = LinearLayoutManager(requireContext())
        rvUsers.adapter = adapter
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.users.collect { users ->
                adapter.submitList(users)
            }
        }
    }


    override fun listeners() = with(binding) {
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }






}