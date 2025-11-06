package com.endrazhafir.siamobile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.endrazhafir.siamobile.ui.theme.SiaMobileTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
