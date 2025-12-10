package com.example.tbcworks.presentation.screens.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.databinding.FragmentHomeBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.SnackBarHelper.showSnackBar
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import com.example.tbcworks.presentation.screens.home.adapter.PostAdapter
import com.example.tbcworks.presentation.screens.home.adapter.StoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    private val storyAdapter by lazy { StoryAdapter() }
    private val postAdapter by lazy { PostAdapter() }

    override fun bind() {
        setupRv()
        observe()
    }
    private fun setupRv() =with(binding){
        rvStories.adapter = storyAdapter
        rvPosts.adapter = postAdapter

        rvStories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvPosts.layoutManager = LinearLayoutManager(requireContext())


        viewModel.onEvent(HomeEvent.LoadPosts)
        viewModel.onEvent(HomeEvent.LoadStories)
    }

    private fun observe() {

        collectStateFlow(viewModel.uiState) { state ->
            postAdapter.submitList(state.posts)
            storyAdapter.submitList(state.stories)

            binding.tvNetworkStatus.text = if (state.isNetworkAvailable) ONLINE else OFFLINE

            state.error?.let { binding.root.showSnackBar(it) }
        }

        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                is HomeSideEffect.ShowMessage -> binding.root.showSnackBar(effect.message)
            }
        }
    }

    companion object{
        private const val ONLINE = "Online"
        private const val OFFLINE = "No internet connection"
    }

}