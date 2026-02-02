package com.example.mycomposeapp.ui.components

import android.R.attr.tint
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.request.ImageRequest
import com.example.mycomposeapp.R
import com.example.mycomposeapp.ui.theme.MyTheme

@Composable
fun NetworkImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    @DrawableRes placeholder: Int = R.drawable.ic_photo
) {
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(placeholder),
        error = painterResource(placeholder),
        contentDescription = null,
        modifier = modifier
            .clip(shape),
        contentScale = ContentScale.Crop,
        colorFilter = tint.let { ColorFilter.tint(MyTheme.colorScheme.onSurface) }
    )
}