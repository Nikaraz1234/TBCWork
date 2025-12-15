package com.example.tbcworks.presentation.screens.worker

import androidx.fragment.app.viewModels
import com.example.tbcworks.databinding.FragmentWorkerBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.SnackBarHelper.showSnackBar
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkerFragment : BaseFragment<FragmentWorkerBinding>(FragmentWorkerBinding::inflate) {

    private val viewModel: WorkerViewModel by viewModels()

    override fun bind() {
        collectStateFlow(viewModel.uiState) { state ->
            binding.tvCleanupStatus.text = when (state) {
                WorkerState.Idle -> "Status: Idle"
                WorkerState.Running -> "Status: Cleaning..."
                WorkerState.Success -> "Status: Cleanup Finished!"
                WorkerState.Failed -> "Status: Cleanup Failed!"
            }
        }

        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                is WorkerSideEffect.ShowSnackBar -> {
                    binding.root.showSnackBar(effect.message)
                }
            }
        }
    }

    override fun listeners() {
        binding.btnCleanup.setOnClickListener {
            viewModel.startCleanup()
        }
    }
}
