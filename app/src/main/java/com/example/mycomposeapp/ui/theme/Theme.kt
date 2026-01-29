package com.example.mycomposeapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val DarkColorScheme = darkColorScheme(
    primary = SelectedItemColor,
    onPrimary = Color(0xFF052018),

    secondary = BottomBarColor,
    onSecondary = DarkTextPrimary,

    background = DarkBackground,
    onBackground = DarkTextPrimary,

    surface = DarkSurface,
    onSurface = DarkTextPrimary,

    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,

    outline = DarkUnselected,
    outlineVariant = DarkOutline
)

val LightColorScheme = lightColorScheme(
    primary = SelectedItemColor,
    onPrimary = Color(0xFF052018),

    secondary = BottomBarColor,
    onSecondary = LightTextPrimary,

    background = LightBackground,
    onBackground = LightTextPrimary,

    surface = LightSurface,
    onSurface = LightTextPrimary,

    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightTextSecondary,

    outline = LightUnselected,
    outlineVariant = LightOutline
)


private val LocalAppColorScheme = staticCompositionLocalOf<ColorScheme> {
    error("No ColorScheme provided. Wrap your UI with MyComposeAppTheme.")
}

private val LocalAppTypography = staticCompositionLocalOf<Typography> {
    error("No Typography provided. Wrap your UI with MyComposeAppTheme.")
}

@Immutable
object MyTheme {
    val colorScheme: ColorScheme
        @Composable get() = LocalAppColorScheme.current

    val typography: Typography
        @Composable get() = LocalAppTypography.current
}

@Composable
fun MyComposeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    typography: Typography = Typography,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides typography
    ) {
        content()
    }
}
