package com.example.ui.screen.form.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ui.screen.form.FormScreen
import kotlinx.serialization.Serializable

@Serializable
data object Form

fun NavGraphBuilder.formGraph(
    snackBarHostState: SnackbarHostState
) {
    composable<Form> {
        FormScreen(
            snackBarHostState = snackBarHostState
        )
    }
}