package com.endrazhafir.siamobile

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
                        Toast.makeText(this, "Tambah Mahasiswa", Toast.LENGTH_SHORT).show()
                        // Navigate to student management
                    },
                    onSubjectClick = {
                        Toast.makeText(this, "Tambah Mata Kuliah", Toast.LENGTH_SHORT).show()
                        // Navigate to subject management
                    },
                    onLecturerClick = {
                        Toast.makeText(this, "Tambah Dosen", Toast.LENGTH_SHORT).show()
                        // Navigate to lecturer management
                    }
                )
            }
        }
    }
}