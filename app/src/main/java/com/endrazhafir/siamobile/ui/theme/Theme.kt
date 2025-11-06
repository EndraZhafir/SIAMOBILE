package com.endrazhafir.siamobile.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = UGNGreen,
    onPrimary = White,
    primaryContainer = UGNGreenDark,
    onPrimaryContainer = White,

    secondary = UGNGold,
    onSecondary = Black,
    secondaryContainer = UGNGoldDark,
    onSecondaryContainer = Black,

    tertiary = UGNGray,
    onTertiary = White,

    background = BackgroundCream,
    onBackground = Black,

    surface = BackgroundCream,
    onSurface = Black,

    error = Color(0xFFBA1A1A),
    onError = White
)

@Composable
fun SiaMobileTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
