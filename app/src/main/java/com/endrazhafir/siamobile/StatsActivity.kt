package com.endrazhafir.siamobile

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.endrazhafir.siamobile.ui.components.CustomToastHost
import com.endrazhafir.siamobile.ui.screens.StatsScreen
import com.endrazhafir.siamobile.ui.theme.SiaMobileTheme
class StatsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                Box(modifier = Modifier.fillMaxSize()) {
                    StatsScreen(
                        type = screenType,
                        onBackClick = { finish() }
                    )

                    Box(modifier = Modifier.align(Alignment.TopCenter)) {
                        CustomToastHost()
                    }
                }
            }
        }
    }
}
