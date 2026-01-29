package com.example.mycomposeapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object Radius {
    val radius16 = RoundedCornerShape(16.dp)
    val radius26 = RoundedCornerShape(26.dp)
    val bottomBarRadius = RoundedCornerShape(
        topStart = 26.dp,
        topEnd = 26.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )
}