package com.endrazhafir.siamobile.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endrazhafir.siamobile.data.LoginRequest
import com.endrazhafir.siamobile.data.remote.RetrofitClient
import com.endrazhafir.siamobile.ui.components.ToastManager
import com.endrazhafir.siamobile.ui.components.ToastType
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
                        ToastManager.show("Login berhasil! Selamat datang.", ToastType.SUCCESS)

                        val roles = apiResponse.data.user.roles

                        // Cek role 'admin' atau 'manager'
                        if (roles.contains("admin") ||  roles.contains("manager")) {

                            val token = apiResponse.data.accessToken
                            val name = apiResponse.data.user.name

                            // Simpan token ke SessionManager
                            val sessionManager = SessionManager(context)
                            sessionManager.saveAuthToken("Bearer $token", name)

                            isLoginSuccess.value = true
                        } else {
                            ToastManager.show("Akses Ditolak: Anda bukan Admin/Manager!", ToastType.ERROR)
                        }

                    } else {
                        val msg = apiResponse?.message ?: "Terjadi kesalahan"
                        ToastManager.show("Login Gagal: $msg", ToastType.ERROR)
                    }

                } else {
                    ToastManager.show("Email atau Password salah.", ToastType.ERROR)
                }

            } catch (e: Exception) {
                ToastManager.show("Terjadi kesalahan koneksi.", ToastType.ERROR)
                e.printStackTrace()

            } finally {
                isLoading.value = false
            }
        }
    }
}