package com.example.tbcworks.ui.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tbcworks.R
import com.example.tbcworks.ui.navigation.Screen

@Composable
fun LoginScreen(navController: NavHostController){
    Column {

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
            text = stringResource(R.string.login_title),
            fontSize = 32.sp,
            modifier = Modifier.padding(16.dp)
        )

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.email_label)) },
            placeholder = { Text(stringResource(R.string.email_placeholder)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password_label)) },
            placeholder = { Text(stringResource(R.string.email_placeholder)) }, // ideally should be password hint
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 5.dp)
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 10.dp),
            onClick = { navController.navigate(Screen.Welcome.route) },
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(
                stringResource(R.string.login_button),
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()

    LoginScreen(navController)
}