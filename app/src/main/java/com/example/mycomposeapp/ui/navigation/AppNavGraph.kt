package com.example.mycomposeapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mycomposeapp.ui.screen.feed.navigation.Feed
import com.example.mycomposeapp.ui.screen.feed.navigation.feedGraph
import kotlinx.serialization.Serializable




@Serializable
data object Likes

@Serializable
data object Comments

@Serializable
data object Notifications

@Composable
fun AppNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    snackBarHostState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        startDestination = Feed,
        modifier = Modifier.padding(paddingValues)
    ) {

        feedGraph(navController = navController, snackBarHostState = snackBarHostState)

        composable<Likes> { }
        composable<Comments> { }
        composable<Notifications> { }
    }
}