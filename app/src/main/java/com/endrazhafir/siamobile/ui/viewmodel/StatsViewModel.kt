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

    // Fungsi fetch (ambil semua matkul)
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

    // Fungsi delete (hapus matkul)
    fun deleteMataKuliah(context: Context, id: Int) {
        val sessionManager = SessionManager(context)
        val token = sessionManager.getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.deleteMataKuliah(token, id)
                if (response.isSuccessful) {
                    android.widget.Toast.makeText(context, "Mata kuliah berhasil dihapus", android.widget.Toast.LENGTH_SHORT).show()
                    getMataKuliah(context)
                } else {
                    android.widget.Toast.makeText(context, "Gagal menghapus mata kuliah: ${response.code()}", android.widget.Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                android.widget.Toast.makeText(context, "Terjadi kesalahan: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi create (tambah matkul)
    fun addMataKuliah(context: Context, matkul: MataKuliah) {
        val sessionManager = SessionManager(context)
        val token = sessionManager.getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.addMataKuliah(token, matkul)
                if (response.isSuccessful) {
                    android.widget.Toast.makeText(context, "Mata kuliah berhasil ditambahkan", android.widget.Toast.LENGTH_SHORT).show()
                    getMataKuliah(context)
                } else {
                    val errorBody = response.errorBody()?.string()
                    android.widget.Toast.makeText(context, "Gagal menambahkan mata kuliah: ${response.code()} - $errorBody", android.widget.Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                android.widget.Toast.makeText(context, "Terjadi kesalahan: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi update (edit matkul)
    fun updateMataKuliah(context: Context, id: Int, matkul: MataKuliah) {
        val sessionManager = SessionManager(context)
        val token = sessionManager.getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.updateMataKuliah(token, id, matkul)
                if (response.isSuccessful) {
                    android.widget.Toast.makeText(context, "Mata kuliah berhasil diupdate", android.widget.Toast.LENGTH_SHORT).show()
                    getMataKuliah(context)
                } else {
                    val errorBody = response.errorBody()?.string()
                    android.widget.Toast.makeText(context, "Gagal mengupdate mata kuliah: ${response.code()} - $errorBody", android.widget.Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                android.widget.Toast.makeText(context, "Terjadi kesalahan: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }
}