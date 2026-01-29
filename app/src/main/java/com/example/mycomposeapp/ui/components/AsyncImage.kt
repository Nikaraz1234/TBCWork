package com.example.mycomposeapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest
import com.example.mycomposeapp.R

@Composable
fun NetworkImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    @DrawableRes placeholder: Int = R.drawable.photo_placeholder
) {
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .placeholder(placeholder)
            .error(placeholder)
            .build(),

        contentDescription = null,
        modifier = modifier
            .clip(shape),
        contentScale = ContentScale.Crop
    )
}