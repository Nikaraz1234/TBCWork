package com.example.tbcworks.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tbcworks.ui.screen.login.LoginScreen
import com.example.tbcworks.ui.screen.register_first.RegisterFirstScreen
import com.example.tbcworks.ui.screen.register_second.RegisterSecondScreen
import com.example.tbcworks.ui.screen.welcome.WelcomeScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) { WelcomeScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.RegisterFirst.route) { RegisterFirstScreen(navController) }
        composable(Screen.RegisterSecond.route) { RegisterSecondScreen(navController) }
    }
}