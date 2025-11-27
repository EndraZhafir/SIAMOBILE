package com.endrazhafir.siamobile.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endrazhafir.siamobile.data.MataKuliah
import com.endrazhafir.siamobile.data.remote.RetrofitClient
import com.endrazhafir.siamobile.utils.SessionManager
import kotlinx.coroutines.launch

class StatsViewModel : ViewModel() {

    var mataKuliahList = mutableStateListOf<MataKuliah>()
        private set

    // Loading state
    var isLoading = mutableStateListOf(false) // Simplifikasi state loading

    fun getMataKuliah(context: Context) {

        // Ambil token dari SessionManager
        val sessionManager = SessionManager(context)
        val token = sessionManager.getToken()

        if (token == null) {
            println("Error: Token tidak ditemukan, user harus login ulang")
            return
        }

        viewModelScope.launch {
            try {
                // Panggil API
                val response = RetrofitClient.instance.getAllMataKuliah(token)

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null && apiResponse.status == "success") {
                        mataKuliahList.clear()
                        mataKuliahList.addAll(apiResponse.data)
                        println("Sukses Fetch Data: ${apiResponse.data.size} items")
                    }
                } else {
                    println("Error API: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("Exception: ${e.message}")
            }
        }
    }

    // ... Fungsi add, update, delete sama logic-nya ...
}