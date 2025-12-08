package com.endrazhafir.siamobile

import android.content.Intent
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
import com.endrazhafir.siamobile.ui.components.ToastManager
import com.endrazhafir.siamobile.ui.components.ToastType
import com.endrazhafir.siamobile.ui.screens.DashboardScreen
import com.endrazhafir.siamobile.ui.theme.SiaMobileTheme
import com.endrazhafir.siamobile.utils.SessionManager

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionManager = SessionManager(this)
        val userName = sessionManager.getUserName() ?: "Admin"

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
                    DashboardScreen(
                        userName = userName,
                        onLogoutClick = {
                            sessionManager.clearSession()

                            ToastManager.show("Logout berhasil. Sampai jumpa!", ToastType.SUCCESS)

                            val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                            startActivity(intent)
                            finish()
                        },
                        onStudentClick = {
                            val intent = Intent(this@DashboardActivity, StatsActivity::class.java).apply {
                                putExtra("SCREEN_TYPE", "MAHASISWA")
                            }
                            startActivity(intent)
                        },
                        onSubjectClick = {
                            val intent = Intent(this@DashboardActivity, StatsActivity::class.java).apply {
                                putExtra("SCREEN_TYPE", "MATAKULIAH")
                            }
                            startActivity(intent)
                        },
                        onLecturerClick = {
                            val intent = Intent(this@DashboardActivity, StatsActivity::class.java).apply {
                                putExtra("SCREEN_TYPE", "DOSEN")
                            }
                            startActivity(intent)
                        }
                    )

                    Box(modifier = Modifier.align(Alignment.TopCenter)) {
                        CustomToastHost()
                    }
                }
            }
        }
    }
}