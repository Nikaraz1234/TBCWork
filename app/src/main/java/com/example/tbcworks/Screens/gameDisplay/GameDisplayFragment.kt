package com.example.tbcworks.Screens.gameDisplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tbcworks.BaseFragment
import com.example.tbcworks.databinding.FragmentGameDisplayBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class GameDisplayFragment : BaseFragment<FragmentGameDisplayBinding>() {
    private val args: GameDisplayFragmentArgs by navArgs()
    private val viewModel: GameViewModel by viewModels()
    private lateinit var adapter: GameAdapter
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGameDisplayBinding {
        return FragmentGameDisplayBinding.inflate(inflater, container, false)
    }

    override fun bind(){
        with(binding) {
            adapter = GameAdapter{cell ->
                viewModel.onCellClicked(cell)
            }

            rvGameBoard.layoutManager = GridLayoutManager(requireContext(), args.col)
            rvGameBoard.adapter = adapter

            viewModel.startGame(args.row, args.col)


            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                    launch {
                        viewModel.cells.collect {
                            adapter.submitList(it)
                        }
                    }
                    launch {
                        viewModel.winner.collect { winner ->
                            if(winner != null){
                                Snackbar.make(binding.root, "$winner wins!", Snackbar.LENGTH_SHORT).show()
                                binding.root.postDelayed({
                                    findNavController().popBackStack()
                                }, 1000)
                            }
                        }
                    }

                }
            }
        }
    }

}