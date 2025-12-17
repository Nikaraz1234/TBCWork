package com.example.tbcworks.presentation.screens.profile

import android.app.AlertDialog
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.R
import com.example.tbcworks.databinding.DialogChangePasswordBinding
import com.example.tbcworks.databinding.DialogDeletePasswordBinding
import com.example.tbcworks.databinding.FragmentProfileBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.SnackBarHelper.showSnackBar
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun listeners() {
        with(binding) {
            btnLogout.setOnClickListener {
                viewModel.onEvent(ProfileEvent.Logout)
            }
            btnDeleteAccount.setOnClickListener {
                showDeleteAccountDialog()
            }
            btnChangePassword.setOnClickListener {
                showChangePasswordDialog()
            }
        }
    }

    override fun bind() {
        viewModel.onEvent(ProfileEvent.LoadProfileData)
        observeState()
        observeSideEffects()
    }



    private fun observeState() = with(binding) {
        collectStateFlow(viewModel.uiState) { state ->
            tvEmail.text = state.email

            tvBalance.text = getString(
                R.string.balance_format,
                state.balance
            )

            tvTransactionsCount.text = getString(
                R.string.transactions_count,
                state.transactionsCount
            )

            tvPotsCount.text = getString(
                R.string.pots_count,
                state.potsCount
            )

            progressBar.visibility =
                if (state.isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun observeSideEffects() {
        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                is ProfileSideEffect.ShowSnackBar ->
                    binding.root.showSnackBar(effect.message)

                is ProfileSideEffect.NavigateToLogin -> {
                    findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment(),
                        NavOptions.Builder()
                            .setPopUpTo(R.id.nav_graph, inclusive = true)
                            .build()
                    )
                }
            }
        }
    }

    private fun showChangePasswordDialog() {
        val dialogBinding = DialogChangePasswordBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnConfirm.setOnClickListener {
            val current = dialogBinding.etCurrentPAssword.text.toString().trim()
            val newPassword = dialogBinding.etPassword.text.toString().trim()
            val repeatPassword = dialogBinding.etRepeatPassword.text.toString().trim()

            if (current.isEmpty() || newPassword.isEmpty() || repeatPassword.isEmpty()) {
                binding.root.showSnackBar("Fill all fields")
                return@setOnClickListener
            }

            if (newPassword != repeatPassword) {
                binding.root.showSnackBar("Passwords do not match")
                return@setOnClickListener
            }

            viewModel.onEvent(ProfileEvent.ChangePassword(current, newPassword))
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDeleteAccountDialog() {
        val dialogBinding = DialogDeletePasswordBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Confirm Account Deletion")
            .setView(dialogBinding.root)
            .setPositiveButton("Delete", null)
            .setNegativeButton("Cancel") { d, _ -> d.dismiss() }
            .create()

        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {
                val currentPassword = dialogBinding.etPassword.text.toString().trim()
                if (currentPassword.isEmpty()) {
                    binding.root.showSnackBar("Enter your current password")
                    return@setOnClickListener
                }
                viewModel.onEvent(ProfileEvent.DeleteAccount(currentPassword))
                dialog.dismiss()
            }
        }

        dialog.show()
    }



}