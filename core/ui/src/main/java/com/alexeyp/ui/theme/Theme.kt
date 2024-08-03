package com.alexeyp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = White,
    onPrimary = Blue,
    secondary = DarkGrey,
    onSecondary = DarkBlue,
    background = Dark,
    surface = Grey,
    onSurface = Light,
    error = Red,
    onError = Red
)

private val LightColorPalette = lightColorScheme(
    primary = Dark,
    onPrimary = Blue,
    secondary = DarkGrey,
    onSecondary = DarkBlue,
    background = White,
    surface = LightGrey,
    onSurface = LightGrey,
    error = Red,
    onError = Red
)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */

@Composable
fun SlideTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorPalette

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}