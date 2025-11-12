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

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    onLogoutClick = {
                        Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show()
                        // Handle logout logic
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