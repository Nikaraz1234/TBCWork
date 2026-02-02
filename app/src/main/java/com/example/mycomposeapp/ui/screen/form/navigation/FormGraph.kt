package com.example.mycomposeapp.ui.screen.form.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mycomposeapp.ui.screen.form.FormScreen
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