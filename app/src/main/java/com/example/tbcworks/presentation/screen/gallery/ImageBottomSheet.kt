package com.example.tbcworks.presentation.screen.gallery

import com.example.tbcworks.databinding.BottomSheetLayoutBinding
import com.example.tbcworks.presentation.common.BaseBottomSheet

class ImageBottomSheet(
    private val onCamera: () -> Unit,
    private val onGallery: () -> Unit
) :
    BaseBottomSheet<BottomSheetLayoutBinding>(BottomSheetLayoutBinding::inflate) {

    override fun listeners() {
        with(binding) {
            btnTakePicture.setOnClickListener {
                onCamera()
                dismiss()
            }
            btnChooseGallery.setOnClickListener {
                onGallery()
                dismiss()
            }
        }
    }
}
