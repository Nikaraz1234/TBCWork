package com.example.mycomposeapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.mycomposeapp.ui.screen.form.navigation.Form
import com.example.mycomposeapp.ui.screen.form.navigation.formGraph

@Composable
fun AppNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    snackBarHostState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        startDestination = Form,
        modifier = Modifier.padding(paddingValues)
    ) {
        formGraph(snackBarHostState = snackBarHostState)
    }
}