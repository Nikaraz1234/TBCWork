package com.example.tbcworks.presentation.screen.gallery

import android.net.Uri

object GalleryContract {

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val selectedImageUri: Uri? = null
    )

    sealed class Event {
        object CameraClicked : Event()
        object GalleryClicked : Event()
        object CameraPermissionGranted : Event()
        data class ImagePicked(val uri: Uri) : Event()
        data class UploadImageClicked(val uri: Uri) : Event()
        data class ImageUploadFailed(val message: String) : Event()
    }

    sealed class SideEffect {
        object RequestCameraPermission : SideEffect()
        object OpenCamera : SideEffect()
        object OpenGallery : SideEffect()
        data class ShowSnackBar(val message: String) : SideEffect()
    }
}