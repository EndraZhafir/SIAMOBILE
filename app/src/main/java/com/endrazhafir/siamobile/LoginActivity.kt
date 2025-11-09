package com.endrazhafir.siamobile

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.endrazhafir.siamobile.ui.screens.LoginScreen
import com.endrazhafir.siamobile.ui.theme.SiaMobileTheme

class LoginActivity : ComponentActivity() {
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
                LoginScreen(
                    onLoginClick = { email, password ->
                        // Handle login logic
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            Toast.makeText(this, "Login Success!", Toast.LENGTH_SHORT).show()
                            // Navigate to Dashboard
                            startActivity(Intent(this, DashboardActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
    }
}
