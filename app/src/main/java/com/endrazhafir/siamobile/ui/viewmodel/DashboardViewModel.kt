package com.endrazhafir.siamobile.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endrazhafir.siamobile.data.DashboardStatistics
import com.endrazhafir.siamobile.data.remote.RetrofitClient
import com.endrazhafir.siamobile.utils.SessionManager
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    // State untuk menyimpan data statistik
    // Inisialisasi dengan 0 semua biar ngga null
    var statistics = mutableStateOf(DashboardStatistics(0, 0, 0))
        private set

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    fun getStatistics(context: Context) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = RetrofitClient.instance.getDashboardStatistics(token)

                if (response.isSuccessful && response.body()?.status == "success") {
                    // Update state dengan data dari API
                    response.body()?.data?.let {
                        statistics.value = it
                    }
                } else {
                    errorMessage.value = "Gagal mengambil statistik: ${response.message()}"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.value = "Error: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}