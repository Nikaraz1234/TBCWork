package com.example.tbcworks.presentation.screens.lock_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.R
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.databinding.FragmentLockScreenBinding
import com.example.tbcworks.presentation.extension.showSnackbar
import com.example.tbcworks.presentation.screens.lock_screen.adapter.DotsAdapter
import com.example.tbcworks.presentation.screens.lock_screen.adapter.NumbersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LockScreenFragment : BaseFragment<FragmentLockScreenBinding>() {

    private val viewModel: LockScreenViewModel by viewModels()

    private val numbersAdapter by lazy {
        NumbersAdapter { btn ->
            viewModel.onEvent(LockScreenEvent.BtnPressed(btn))
        }
    }

    private val dotsAdapter by lazy { DotsAdapter() }


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLockScreenBinding {
        return FragmentLockScreenBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRVs()
        observeState()
        observeSideEffects()
    }
    private fun setUpRVs() = with(binding) {
        rvDots.adapter = dotsAdapter
        rvDots.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        rvNumbers.adapter = numbersAdapter
        rvNumbers.layoutManager = GridLayoutManager(requireContext(), 3)
    }
    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    dotsAdapter.submitList(state.dots)
                    numbersAdapter.submitList(state.keys)
                }
            }
        }
    }
    private fun observeSideEffects() = with(binding){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.sideEffect.collect { effect ->
                    when (effect) {
                        LockScreenSideEffect.WrongPasscode -> {
                            shakeDots()
                            root.showSnackbar(R.string.passcode_incorrect)
                        }

                        LockScreenSideEffect.CorrectPasscode -> {
                            root.showSnackbar(R.string.passcode_correct)
                        }

                        LockScreenSideEffect.FingerprintClicked -> {
                            root.showSnackbar(R.string.wrong_fingerprint)
                        }
                    }
                }
            }
        }
    }
    private fun shakeDots() {
        val animation = android.view.animation.TranslateAnimation(
            -10f, 10f, 0f, 0f
        ).apply {
            duration = 50
            repeatMode = android.view.animation.Animation.REVERSE
            repeatCount = 5
        }
        binding.rvDots.startAnimation(animation)
    }
}
