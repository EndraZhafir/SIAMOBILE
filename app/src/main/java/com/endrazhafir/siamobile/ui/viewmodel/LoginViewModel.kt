package com.endrazhafir.siamobile.ui.viewmodel

import android.content.Context
import android.util.Log.e
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endrazhafir.siamobile.data.LoginRequest
import com.endrazhafir.siamobile.data.remote.RetrofitClient
import com.endrazhafir.siamobile.utils.SessionManager
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var isLoading = mutableStateOf(false)
    var loginError = mutableStateOf("")
    var isLoginSuccess = mutableStateOf(false)

    fun login(context: Context, email: String, pass: String) {
        isLoading.value = true
        loginError.value = ""

        viewModelScope.launch {
            try {
                val request = LoginRequest(email, pass)
                val response = RetrofitClient.instance.login(request)

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null && apiResponse.status == "success") {
                        // Cek roles
                        val roles = apiResponse.data.user.roles

                        // Cek apakah punya role 'manager' atau 'admin'
                        // Sesuaikan string 'manager' dengan yang ada di database Laravel kamu
                        if (roles.contains("manager") || roles.contains("admin")) {

                            // Ambil Token & Nama
                            val token = apiResponse.data.accessToken
                            val name = apiResponse.data.user.name

                            // Simpan ke SessionManager
                            val sessionManager = SessionManager(context)
                            sessionManager.saveAuthToken("Bearer $token", name)

                            // Trigger navigasi
                            isLoginSuccess.value = true
                        } else {
                            // Jika login sukses tapi role bukan manager
                            loginError.value = "Akses Ditolak: Anda bukan Admin/Manager!"
                        }

                    } else {
                        loginError.value = "Login Gagal: ${apiResponse?.message}"
                    }

                } else {
                    loginError.value = "Email atau Password Salah"
                }

            } catch (e: Exception) {
                loginError.value = "Terjadi kesalahan koneksi"
                e.printStackTrace()

            } finally {
                isLoading.value = false
            }
        }
    }
}