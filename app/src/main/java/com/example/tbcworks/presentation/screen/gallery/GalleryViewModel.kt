package com.example.tbcworks.presentation.screen.gallery

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.usecase.CompressImageUseCase
import com.example.tbcworks.domain.usecase.UploadImageUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.presentation.screen.gallery.GalleryContract.SideEffect.OpenCamera
import com.example.tbcworks.presentation.screen.gallery.GalleryContract.SideEffect.OpenGallery
import com.example.tbcworks.presentation.screen.gallery.GalleryContract.SideEffect.RequestCameraPermission
import com.example.tbcworks.presentation.screen.gallery.GalleryContract.SideEffect.ShowSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase,
    private val compressImageUseCase: CompressImageUseCase
) :
    BaseViewModel<GalleryContract.State, GalleryContract.SideEffect, GalleryContract.Event>(
        GalleryContract.State()
    ) {

    fun onEvent(event: GalleryContract.Event) {
        when (event) {

            GalleryContract.Event.CameraClicked -> {
                sendSideEffect(RequestCameraPermission)
            }

            GalleryContract.Event.GalleryClicked -> {
                sendSideEffect(OpenGallery)
            }

            is GalleryContract.Event.ImagePicked -> {
                val compressedUri = compressImageUseCase.execute(event.uri)
                setState { copy(selectedImageUri = compressedUri) }
            }

            is GalleryContract.Event.ImageUploadFailed -> {
                setState { copy(error = event.message) }
                sendSideEffect(ShowSnackBar(event.message))
            }

            GalleryContract.Event.CameraPermissionGranted -> sendSideEffect(OpenCamera)

            is GalleryContract.Event.UploadImageClicked -> {
                uploadImage(event.uri)
            }

        }
    }

    private fun uploadImage(uri: Uri) {
        setState { copy(isLoading = true) }

        viewModelScope.launch {
            val result = uploadImageUseCase(uri)
            setState { copy(isLoading = false) }

            if (result.isSuccess) {
                sendSideEffect(ShowSnackBar(STR_UPLOAD_STARTED))
            } else {
                sendSideEffect(ShowSnackBar(STR_UPLOAD_FAILED))
            }
        }
    }

    companion object {
        private const val STR_UPLOAD_STARTED = "Upload started"
        private const val STR_UPLOAD_FAILED = "Upload failed"
    }
}
