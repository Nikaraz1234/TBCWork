package com.example.tbcworks.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tbcworks.ui.screen.dashboard.DashboardScreen
import com.example.tbcworks.ui.screen.login.LoginScreen
import com.example.tbcworks.ui.screen.register_first.RegisterFirstScreen
import com.example.tbcworks.ui.screen.welcome.WelcomeScreen

@Composable
fun AppNavGraph(
    paddingValues: PaddingValues,
    snackBarHostState: SnackbarHostState
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(
                navController,
                snackBarHostState
            )
        }

        composable(Screen.RegisterFirst.route) {
            RegisterFirstScreen(
                navController = navController,
                snackBarHostState = snackBarHostState
            )
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController)
        }
    }
}