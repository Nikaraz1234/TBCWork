package com.example.tbcworks.ui.screen.register_first

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tbcworks.R
import com.example.tbcworks.ui.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun RegisterFirstScreen(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    viewModel: RegisterFirstViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is RegisterFirstContract.SideEffect.NavigateToLogin -> {
                    navController.navigate(Screen.Login.route)
                }

                is RegisterFirstContract.SideEffect.ShowError -> {
                    scope.launch {
                        snackBarHostState.showSnackbar(effect.message)
                    }
                }
            }
        }
    }

    RegisterFirstContent(
        email = state.email,
        password = state.password,
        onEmailChange = {
            viewModel.onEvent(RegisterFirstContract.Event.EnterEmail(it))
        },
        onPasswordChange = {
            viewModel.onEvent(RegisterFirstContract.Event.EnterPassword(it))
        },
        onBackClick = {
            navController.popBackStack(Screen.Welcome.route, inclusive = false)
        },
        onNextClick = {
            viewModel.onEvent(RegisterFirstContract.Event.NextClicked)
        }
    )
}


@Composable
fun RegisterFirstContent(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(R.drawable.btn_back),
                contentDescription = stringResource(R.string.back_button_desc),
                tint = Color.Black
            )
        }

        Text(
            text = stringResource(R.string.register_title),
            fontSize = 32.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text(stringResource(R.string.email_label)) },
            placeholder = { Text(stringResource(R.string.email_placeholder)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text(stringResource(R.string.password_label)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onNextClick,
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(text = stringResource(R.string.register_title))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterFirstContentPreview() {
    RegisterFirstContent(
        email = "",
        password = "",
        onEmailChange = {},
        onPasswordChange = {},
        onBackClick = {},
        onNextClick = {}
    )
}
