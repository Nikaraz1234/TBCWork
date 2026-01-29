package com.example.mycomposeapp.ui.screen.feed.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mycomposeapp.ui.screen.feed.FeedScreen
import kotlinx.serialization.Serializable

@Serializable
data object Feed

fun NavGraphBuilder.feedGraph(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState
) {
    composable<Feed> {
        FeedScreen(
            navController = navController,
            snackBarHostState = snackBarHostState
        )
    }
}