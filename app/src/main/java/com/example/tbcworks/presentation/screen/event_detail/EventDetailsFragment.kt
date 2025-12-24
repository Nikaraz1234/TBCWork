package com.example.tbcworks.presentation.screen.event_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.databinding.FragmentEventDetailsBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import com.example.tbcworks.presentation.extension.loadImage
import com.example.tbcworks.presentation.extension.showSnackBar
import com.example.tbcworks.presentation.mapper.toDomain
import com.example.tbcworks.presentation.screen.event_detail.adapter.AgendaAdapter
import com.example.tbcworks.presentation.screen.event_detail.adapter.SpeakerAdapter

class EventDetailsFragment : BaseFragment<FragmentEventDetailsBinding>(
    FragmentEventDetailsBinding::inflate
) {

    private val viewModel: EventDetailsViewModel by viewModels()
    private val args: EventDetailsFragmentArgs by navArgs()
    private val agendaAdapter by lazy { AgendaAdapter() }
    private val speakerAdapter by lazy {
        SpeakerAdapter()
    }

    override fun listeners() {
        binding.btnBack.setOnClickListener {
            viewModel.onEvent(EventDetailsContract.Event.BackClicked)
        }

        binding.btnRegister.setOnClickListener {
            viewModel.onEvent(EventDetailsContract.Event.RegisterClicked)
        }
    }

    override fun bind() {
        viewModel.onEvent(EventDetailsContract.Event.Load(args.eventId))
        setUpRv()
        observe()

    }

    private fun setUpRv(){
        binding.rvAgenda.apply {
            adapter = agendaAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        binding.rvSpeakers.apply {
            adapter = speakerAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

    }
    private fun observe(){
        collectStateFlow(viewModel.uiState) { state ->
            binding.apply {
                if (state.isLoading) {
                    // binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                }

                tvEventTitle.text = state.title
                ivBanner.loadImage(state.bannerUrl)
                tvEventDate.text = state.eventDate
                tvEventTime.text = state.eventTime
                tvLocation.text = state.location
                tvCapacity.text = state.capacity
                tvAboutDescription.text = state.aboutDescription

                binding.rvAgenda.adapter?.let { adapter ->
                    if (adapter is AgendaAdapter) {
                        adapter.submitList(state.agenda)
                    }
                }

                binding.rvSpeakers.adapter?.let { adapter ->
                    if (adapter is SpeakerAdapter) {
                        adapter.submitList(state.speakers)
                    }
                }
            }
        }

        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                is EventDetailsContract.Effect.NavigateBack -> {
                    findNavController().popBackStack()
                }
                is EventDetailsContract.Effect.ShowMessage -> {
                    binding.root.showSnackBar(effect.message)
                }
                is EventDetailsContract.Effect.ShowError -> {
                    binding.root.showSnackBar(effect.message)
                }
            }
        }
    }
}
