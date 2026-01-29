package com.example.mycomposeapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.mycomposeapp.ui.navigation.AppNavGraph
import com.example.mycomposeapp.ui.theme.MyComposeAppTheme
import com.example.mycomposeapp.ui.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyComposeAppTheme {
                val snackBarHostState = remember { SnackbarHostState() }
                val navController = rememberNavController()

                Scaffold(
                    containerColor = MyTheme.colorScheme.background,
                    snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
                    bottomBar = { AppBottomBar(navController = navController) }
                ) { paddingValues ->
                    AppNavGraph(
                        navController = navController,
                        paddingValues = paddingValues,
                        snackBarHostState = snackBarHostState
                    )
                }
            }
        }

    }
}