package com.example.tbcworks.presentation.screens.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.data.common.network.NetworkListener
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.databinding.FragmentDashboardBinding
import com.example.tbcworks.presentation.extensions.SnackBarHelper.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var adapter: DashboardAdapter
    @Inject
    lateinit var networkListener: NetworkListener

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)
    }
    override fun bind() {
        setUp()
        observe()

    }

    private fun setUp(){
        setupRv()
        setupNetwork()
    }

    private fun setupNetwork(){
        if (networkListener.isConnected()) {
            viewModel.onEvent(DashboardEvent.FetchUsers)
        } else {
            binding.root.showSnackBar(NO_INTERNET_CONNECTION)
        }

        networkListener.registerNetworkCallback {
            requireActivity().runOnUiThread {
                viewModel.onEvent(DashboardEvent.FetchUsers)
            }
        }
    }
    private fun setupRv() = with(binding) {
        adapter = DashboardAdapter()
        rvUsers.layoutManager = LinearLayoutManager(requireContext())
        rvUsers.adapter = adapter
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.usersPaging.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
    }


    override fun listeners() = with(binding) {
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }



    companion object{
        const val NO_INTERNET_CONNECTION = "No internet connection"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        networkListener.unregisterNetworkCallback()
    }


}