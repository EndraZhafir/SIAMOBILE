package com.endrazhafir.siamobile

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
                DashboardScreen(
                    userName = userName,
                    onLogoutClick = {
                        sessionManager.clearSession()

                        val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                        Toast.makeText(this, "Berhasil Logout", Toast.LENGTH_SHORT).show()

                        startActivity(intent)
                        finish()
                    },
                    onStudentClick = {
                        val intent = Intent(this, StatsActivity::class.java).apply {
                            putExtra("SCREEN_TYPE", "MAHASISWA")
                        }
                        startActivity(intent)
                    },
                    onSubjectClick = {
                        val intent = Intent(this, StatsActivity::class.java).apply {
                            putExtra("SCREEN_TYPE", "MATAKULIAH")
                        }
                        startActivity(intent)
                    },
                    onLecturerClick = {
                        val intent = Intent(this, StatsActivity::class.java).apply {
                            putExtra("SCREEN_TYPE", "DOSEN")
                        }
                        startActivity(intent)
                    }
                )
            }
        }
    }
}