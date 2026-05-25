package com.example.nearmeet.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryPurple,
    secondary = SecondaryPink,
    tertiary = NearMeetAccent,
    background = Color(0xFF0F0F0F),
    surface = Color(0xFF1A1A1A),
    onPrimary = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryPurple,
    secondary = SecondaryPink,
    tertiary = NearMeetAccent,
    background = NearMeetBackground,
    surface = NearMeetSurface,
    onPrimary = Color.White,
    onSurface = Color.Black
)

@Composable
fun NearMeetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
