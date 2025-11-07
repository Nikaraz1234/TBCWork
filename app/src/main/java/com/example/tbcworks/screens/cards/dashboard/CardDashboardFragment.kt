package com.example.tbcworks.screens.cards.dashboard

import CardViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.BaseFragment
import com.example.tbcworks.R
import com.example.tbcworks.databinding.FragmentCardDashboardBinding
import com.example.tbcworks.screens.cards.Card
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CardDashboardFragment : BaseFragment<FragmentCardDashboardBinding>() {
    private val viewModel: CardViewModel by viewModels()
    private lateinit var adapter: CardAdapter
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCardDashboardBinding {
        return FragmentCardDashboardBinding.inflate(inflater, container, false)
    }

    override fun bind() = with(binding){
        adapter = CardAdapter()

        rvCards.adapter = adapter
        rvCards.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        setup()


        parentFragmentManager.setFragmentResultListener(
            "cardRequestKey",
            viewLifecycleOwner
        ) { _, bundle ->
            val newCard = bundle.getSerializable("card") as? Card
            newCard?.let {
                viewModel.addCard(it)
            }
        }

    }


    private fun setup(){
        observe()
    }

    override fun listeners() = with(binding){
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_cardDashboardFragment_to_addCardFragment)
        }
    }
    private fun observe(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cards.collectLatest { cards ->
                    adapter.submitList(cards)
                }
            }
        }
    }

}