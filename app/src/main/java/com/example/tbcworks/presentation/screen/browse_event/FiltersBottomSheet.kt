package com.example.tbcworks.presentation.screen.browse_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tbcworks.databinding.FragmentFiltersBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FiltersBottomSheet(
    private val onFilterSelected: (online: Boolean, offline: Boolean) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentFiltersBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnApplyFilters.setOnClickListener {
            val onlineSelected = binding.cbOnline.isChecked
            val offlineSelected = binding.cbOffline.isChecked

            onFilterSelected(onlineSelected, offlineSelected)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}