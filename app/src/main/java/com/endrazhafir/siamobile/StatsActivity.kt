package com.endrazhafir.siamobile

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.endrazhafir.siamobile.ui.screens.StatsScreen
import com.endrazhafir.siamobile.ui.theme.SiaMobileTheme
class StatsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // membaca "pesan" dari intent. diberi value default "MATAKULIAH" klo g ada "pesan"
        val screenType = intent.getStringExtra("SCREEN_TYPE") ?: "MATAKULIAH"

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        setContent {
            SiaMobileTheme {
                StatsScreen(
                    type = screenType,
                    onBackClick = { finish() }
                )
            }
        }
    }
}
