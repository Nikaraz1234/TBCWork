package com.example.composeapp.ui.naviagtion

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
}
