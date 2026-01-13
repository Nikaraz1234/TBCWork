package com.example.tbcworks.presentation.screen.gallery

import android.Manifest
import android.net.Uri
import android.os.Environment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.tbcworks.R
import com.example.tbcworks.databinding.FragmentGalleryBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.SnackBarHelper.showSnackBar
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class GalleryFragment : BaseFragment<FragmentGalleryBinding>(
    FragmentGalleryBinding::inflate
) {

    private val viewModel: GalleryViewModel by viewModels()
    private lateinit var photoUri: Uri


    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                viewModel.onEvent(GalleryContract.Event.CameraPermissionGranted)
            } else {
                binding.root.showSnackBar(
                    getString(R.string.camera_permission_denied)
                )
            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                viewModel.onEvent(GalleryContract.Event.ImagePicked(photoUri))
            }
        }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                viewModel.onEvent(GalleryContract.Event.ImagePicked(it))
            }
        }

    override fun listeners() {
        with(binding) {
            btnAdd.setOnClickListener {
                ImageBottomSheet(
                    onCamera = { viewModel.onEvent(GalleryContract.Event.CameraClicked) },
                    onGallery = { viewModel.onEvent(GalleryContract.Event.GalleryClicked) }
                ).show(childFragmentManager, getString(R.string.image_bottom_sheet_tag))
            }

            btnUploadImage.setOnClickListener {
                viewModel.uiState.value.selectedImageUri?.let { uri ->
                    viewModel.onEvent(GalleryContract.Event.UploadImageClicked(uri))
                }
            }
        }
    }

    override fun bind() {
        observers()
    }

    private fun observers() {
        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                GalleryContract.SideEffect.RequestCameraPermission -> {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }

                GalleryContract.SideEffect.OpenCamera -> {
                    openCamera()
                }

                GalleryContract.SideEffect.OpenGallery -> {
                    galleryLauncher.launch(getString(R.string.image_mime_type))
                }

                is GalleryContract.SideEffect.ShowSnackBar -> {
                    binding.root.showSnackBar(effect.message)
                }
            }
        }

        collectStateFlow(viewModel.uiState) { state ->
            with(binding) {
                ivSelectedImage.isVisible = state.selectedImageUri != null
                btnUploadImage.isVisible = state.selectedImageUri != null
                state.selectedImageUri?.let { ivSelectedImage.setImageURI(it) }
                progressBar.isVisible = state.isLoading
            }
        }
    }

    private fun openCamera() {
        val file = File(
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            getString(R.string.photo_file_name, System.currentTimeMillis())
        )

        photoUri = FileProvider.getUriForFile(
            requireContext(),
            getString(R.string.file_provider_authority, requireContext().packageName),
            file
        )

        cameraLauncher.launch(photoUri)
    }
}
