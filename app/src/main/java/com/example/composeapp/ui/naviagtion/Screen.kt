package com.example.composeapp.ui.naviagtion

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object RegisterFirst : Screen("register_first")
    object Dashboard : Screen("dashboard")
}
