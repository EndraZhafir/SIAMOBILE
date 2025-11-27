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
import com.endrazhafir.siamobile.ui.viewmodel.LoginViewModel
import com.endrazhafir.siamobile.utils.SessionManager

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ngecek klo udh login, langsung di-redirect ke Dashboard
        val session = SessionManager(this)
        if (session.isLoggedIn()) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
            return
        }

        val viewModel = LoginViewModel()

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
                // Observe state sukses
                if (viewModel.isLoginSuccess.value) {
                    // Pindah ke halaman Dashboard Admin
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                }

                LoginScreen(
                    // Kirim state loading/error ke UI kalau mau ditampilkan
                    // errorMessage = viewModel.loginError.value,

                    onLoginClick = { email, password ->
                        // Handle login logic dengan memanggil fun login di viewModel
                        viewModel.login(this@LoginActivity, email, password)
                    }
                )

                // Tampilkan Toast error jika ada
                if (viewModel.loginError.value.isNotEmpty()) {
                    Toast.makeText(this, viewModel.loginError.value, Toast.LENGTH_SHORT).show()
                    viewModel.loginError.value = ""
                }
            }
        }
    }
}
