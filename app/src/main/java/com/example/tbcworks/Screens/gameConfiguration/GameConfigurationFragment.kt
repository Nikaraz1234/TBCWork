package com.example.tbcworks.Screens.gameConfiguration

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.BaseFragment
import com.example.tbcworks.databinding.FragmentGameConfigurationBinding

class GameConfigurationFragment : BaseFragment<FragmentGameConfigurationBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGameConfigurationBinding {
        return FragmentGameConfigurationBinding.inflate(inflater, container, false)
    }

    override fun listeners() = with(binding){
        btnSubmit.setOnClickListener {
            submit()
        }
    }

    private fun submit() = with(binding){
        val selectedDimension = spinnerGameDimensions.selectedItem?.toString()?.trim()  ?: "3x3"

        val action = when (selectedDimension) {
            "3x3" -> GameConfigurationFragmentDirections
                .actionGameConfigurationFragmentToGameDisplayFragment(3, 3)

            "4x4" -> GameConfigurationFragmentDirections
                .actionGameConfigurationFragmentToGameDisplayFragment(4, 4)

            "5x5" -> GameConfigurationFragmentDirections
                .actionGameConfigurationFragmentToGameDisplayFragment(5, 5)
            else -> GameConfigurationFragmentDirections
                .actionGameConfigurationFragmentToGameDisplayFragment(3, 3)
        }
        findNavController().navigate(action)
    }

}