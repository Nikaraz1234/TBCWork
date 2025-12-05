package com.example.tbcworks.presentation.screens.home

import LocationPagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.tbcworks.databinding.FragmentHomeBinding
import com.example.tbcworks.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val adapter by lazy { LocationPagerAdapter() }
    private val viewModel: HomeViewModel by viewModels()

    override fun bind() = with(binding){
        setUpPager()
        observeState()

        viewModel.loadLocations()
    }
    private fun setUpPager() = with(binding){
        viewPager.adapter = adapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.offscreenPageLimit = 3
    }

    private fun observeState(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    adapter.submitList(state.locations)
                }
            }
        }

    }

}
