package com.example.tbcworks.ui.screen.welcome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tbcworks.R
import com.example.tbcworks.ui.navigation.Screen

@Composable
fun WelcomeScreen(
    navController: NavHostController
) {
    WelcomeContent(
        onLoginClick = {
            navController.navigate(Screen.Login.route)
        },
        onRegisterClick = {
            navController.navigate(Screen.RegisterFirst.route)
        }
    )
}
@Composable
fun WelcomeContent(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val buttonShape = RoundedCornerShape(6.dp)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.welcomebg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Image(
            painter = painterResource(R.drawable.welcome_logo),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center)
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onLoginClick,
                shape = buttonShape,
                border = BorderStroke(2.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(stringResource(R.string.login_button))
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = onRegisterClick,
                shape = buttonShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(stringResource(R.string.register_button))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeContentPreview() {
    WelcomeContent(
        onLoginClick = {},
        onRegisterClick = {}
    )
}