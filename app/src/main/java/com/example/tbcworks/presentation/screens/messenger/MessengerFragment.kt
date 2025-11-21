package com.example.tbcworks.presentation.screens.messenger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.databinding.FragmentMessengerBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extensions.SnackBarHelper.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MessengerFragment : BaseFragment<FragmentMessengerBinding>() {

    private val viewModel: MessengerViewModel by viewModels()
    private lateinit var adapter: MessengerAdapter


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMessengerBinding {
        return FragmentMessengerBinding.inflate(inflater, container, false)
    }

    override fun bind() = with(binding){
        super.bind()


        adapter = MessengerAdapter()
        rvUsers.adapter = adapter
        rvUsers.layoutManager = LinearLayoutManager(requireContext())

        viewModel.onEvent(MessengerEvent.LoadUsers)


        observeSideEffect()
        observeState()
    }

    private fun observeState(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                adapter.submitList(state.users)
            }
        }

    }
    private fun observeSideEffect(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sideEffect.collect { effect ->
                when (effect) {
                    is MessengerSideEffect.ShowError ->
                        binding.root.showSnackBar(effect.message)
                }
            }
        }

    }

    override fun listeners() =with(binding) {
        btnFilter.setOnClickListener {
            filter()
        }
    }
    private fun filter() = with(binding){
        val searchText = etSearch.text.toString().trim()
        viewModel.onEvent(MessengerEvent.FilterUsers(searchText))
    }

}