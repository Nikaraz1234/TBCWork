package com.example.tbcworks.presentation.screen.browse_by_category

import androidx.navigation.fragment.findNavController
import com.example.tbcworks.databinding.FragmentBrowseByCategoryBinding
import com.example.tbcworks.presentation.common.BaseFragment

class BrowseByCategoryFragment : BaseFragment<FragmentBrowseByCategoryBinding>(
    FragmentBrowseByCategoryBinding::inflate
) {

    override fun listeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}