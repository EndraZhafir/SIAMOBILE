package com.endrazhafir.siamobile.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun SiaMobileTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = Typography, // dari Type.kt
        content = content
    )
}
