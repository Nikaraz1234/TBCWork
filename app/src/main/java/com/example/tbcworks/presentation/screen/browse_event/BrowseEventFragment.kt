package com.example.tbcworks.presentation.screen.browse_event

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.databinding.FragmentBrowseEventBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.screen.browse_event.adapter.CategoryAdapter
import com.example.tbcworks.presentation.screen.browse_event.adapter.EventAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BrowseEventFragment : BaseFragment<FragmentBrowseEventBinding>(
    FragmentBrowseEventBinding::inflate
) {

    private val viewModel: BrowseEventViewModel by viewModels()

    private val eventAdapter: EventAdapter by lazy {
        EventAdapter { /* event click if needed */ }
    }

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter { categoryPosition ->
            val categoryName = viewModel.uiState.value.categories[categoryPosition]
            viewModel.onEvent(BrowseEventContract.Event.CategorySelected(categoryName))
        }
    }

    override fun bind() {
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.rvEvents.adapter = eventAdapter
        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())

        binding.rvCategories.adapter = categoryAdapter
        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    override fun listeners() {
        binding.etSearch.addTextChangedListener { text ->
            viewModel.onEvent(BrowseEventContract.Event.SearchQueryChanged(text.toString()))
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                eventAdapter.submitList(state.filteredEvents)
                val categoryNames = state.categories
                categoryAdapter.submitList(categoryNames)
            }
        }

        lifecycleScope.launch {
            viewModel.sideEffect.collectLatest { effect ->
                if (effect is BrowseEventContract.SideEffect.ShowError) {
                    // TODO: show toast/snackbar
                }
            }
        }
    }
}
