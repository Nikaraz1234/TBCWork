package com.example.tbcworks.presentation.screen.home

import com.example.tbcworks.databinding.FragmentEventHubBinding
import com.example.tbcworks.presentation.common.BaseFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import com.example.tbcworks.presentation.screen.home.adapter.CategoryAdapter
import com.example.tbcworks.presentation.screen.home.adapter.FAQAdapter
import com.example.tbcworks.presentation.screen.home.adapter.ParentAdapter
import com.example.tbcworks.presentation.screen.home.adapter.TrendingAdapter
import com.example.tbcworks.presentation.screen.home.adapter.UpcomingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventHubFragment : BaseFragment<FragmentEventHubBinding>(
    FragmentEventHubBinding::inflate
) {
    private val viewModel: EventHubViewModel by viewModels()

    private val categoryAdapter by lazy {
        CategoryAdapter(
            onClick = { category ->
                viewModel.onEvent(
                    EventHubContract.Event.CategoryClicked(category)
                )
            }
        )
    }

    private val upcomingAdapter by lazy {
        UpcomingAdapter(
            onEventClick = { event ->
                viewModel.onEvent(
                    EventHubContract.Event.EventClicked(event.id)
                )
            }
        )
    }
    private val trendingAdapter by lazy {
        TrendingAdapter(
            onEventClick = { event ->
                viewModel.onEvent(
                    EventHubContract.Event.EventClicked(event.id)
                )
            }
        )
    }

    override fun listeners() = with(binding){
        tvViewALl.setOnClickListener {
            findNavController().navigate(EventHubFragmentDirections.actionEventHubFragmentToBrowseByCategoryFragment())
        }
    }
    private val faqAdapter by lazy {
        FAQAdapter()
    }


    override fun bind() {
        setup()
    }
     private fun setup() {
        setupRecycler()
        observeState()
        observeSideEffects()

        viewModel.onEvent(EventHubContract.Event.LoadData)
    }

    private fun setupRecycler() = with(binding) {
        rvUpcomingEvents.adapter = upcomingAdapter
        rvUpcomingEvents.layoutManager = LinearLayoutManager(requireContext())

        rvBrowseByCategory.adapter = categoryAdapter
        rvBrowseByCategory.layoutManager = GridLayoutManager(requireContext(), 3)

        rvTrendingEvents.adapter = trendingAdapter
        rvTrendingEvents.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        rvFaq.adapter = faqAdapter
        rvFaq.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeState() {
        collectStateFlow(viewModel.uiState) { state ->
            upcomingAdapter.submitList(state.upcomingEvents)
            categoryAdapter.submitList(state.categories)
            trendingAdapter.submitList(state.trendingEvents)
            faqAdapter.submitList(state.faqs)
        }
    }

    private fun observeSideEffects() {
        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                is EventHubContract.SideEffect.NavigateToEvent -> {
                    val action = EventHubFragmentDirections
                        .actionEventHubFragmentToEventDetailsFragment(effect.eventId)
                    findNavController().navigate(action)
                }
                is EventHubContract.SideEffect.NavigateToCategory -> {
                    // navigate to category screen
                }
            }
        }
    }
}
