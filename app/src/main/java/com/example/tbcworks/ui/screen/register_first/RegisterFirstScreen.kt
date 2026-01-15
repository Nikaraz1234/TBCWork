package com.example.tbcworks.ui.screen.register_first

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tbcworks.R
import com.example.tbcworks.ui.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterFirstScreen(
    navController: NavHostController,
    viewModel: RegisterFirstViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is RegisterFirstContract.SideEffect.NavigateToRegisterSecond -> {
                    navController.navigate(Screen.RegisterSecond.route)
                }
                is RegisterFirstContract.SideEffect.ShowError -> {
                    scope.launch {
                        snackBarHostState.showSnackbar(effect.message)
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            IconButton(
                onClick = { navController.popBackStack(Screen.Welcome.route, inclusive = false) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.btn_back),
                    contentDescription = stringResource(R.string.back_button_desc),
                    tint = Color.Black
                )
            }

            Text(
                text = stringResource(R.string.register_title),
                fontSize = 32.sp,
                modifier = Modifier.padding(16.dp)
            )

            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(RegisterFirstContract.Event.EnterEmail(it)) },
                label = { Text(stringResource(R.string.email_label)) },
                placeholder = { Text(stringResource(R.string.email_placeholder)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onEvent(RegisterFirstContract.Event.EnterPassword(it)) },
                label = { Text(stringResource(R.string.password_label)) },
                placeholder = { Text(stringResource(R.string.password_label)) }, // fixed
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 5.dp)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 10.dp),
                onClick = { viewModel.onEvent(RegisterFirstContract.Event.NextClicked) },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    stringResource(R.string.next_button),
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun RegisterFirstScreenPreview() {
    val navController = rememberNavController()
    RegisterFirstScreen(
        navController = navController
    )
}