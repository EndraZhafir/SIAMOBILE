package com.endrazhafir.siamobile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.endrazhafir.siamobile.ui.theme.SiaMobileTheme

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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