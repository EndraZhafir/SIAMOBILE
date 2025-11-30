package com.endrazhafir.siamobile.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endrazhafir.siamobile.data.AddDosenRequest
import com.endrazhafir.siamobile.data.AddMahasiswaRequest
import com.endrazhafir.siamobile.data.Dosen
import com.endrazhafir.siamobile.data.Mahasiswa
import com.endrazhafir.siamobile.data.MataKuliah
import com.endrazhafir.siamobile.data.remote.RetrofitClient
import com.endrazhafir.siamobile.utils.SessionManager
import kotlinx.coroutines.launch

class StatsViewModel : ViewModel() {

    var mahasiswaList = mutableStateListOf<Mahasiswa>()
        private set
    var mataKuliahList = mutableStateListOf<MataKuliah>()
        private set

    var dosenList = mutableStateListOf<Dosen>()
        private set

    var isLoading = mutableStateListOf(false)

    fun addMahasiswa(context : Context, request: AddMahasiswaRequest) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.addMahasiswa(token, request)
                if (response.isSuccessful) {
                    android.widget.Toast.makeText(context, "Mahasiswa berhasil ditambahkan", android.widget.Toast.LENGTH_SHORT).show()
                    getMahasiswa(context)
                } else {
                    val errorBody = response.errorBody()?.string()
                    android.widget.Toast.makeText(context, "Gagal menambahkan mahasiswa: ${response.code()} - $errorBody", android.widget.Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getMahasiswa(context: Context) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getUsersByRole(token, "mahasiswa")

                if (response.isSuccessful && response.body()?.status == "success") {
                    val rawData = response.body()!!.data

                    val mappedList = rawData.map { it.toMahasiswa() }

                    mahasiswaList.clear()
                    mahasiswaList.addAll(mappedList)
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun toggleStatusMahasiswa(context: Context, id: Int) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.toggleStatusUser(token, id)

                if (response.isSuccessful) {
                    android.widget.Toast.makeText(context, "Status berhasil diubah!", android.widget.Toast.LENGTH_SHORT).show()
                    getMahasiswa(context)
                } else {
                    val errorBody = response.errorBody()?.string()
                    android.widget.Toast.makeText(context, "Gagal: ${response.code()} - $errorBody", android.widget.Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

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

    fun addDosen(context : Context, request: AddDosenRequest) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.addDosen(token, request)
                if (response.isSuccessful) {
                    android.widget.Toast.makeText(context, "Dosen berhasil ditambahkan", android.widget.Toast.LENGTH_SHORT).show()
                    getDosen(context)
                } else {
                    val errorBody = response.errorBody()?.string()
                    android.widget.Toast.makeText(context, "Gagal menambahkan dosen: ${response.code()} - $errorBody", android.widget.Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getDosen(context: Context) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getUsersByRole(token, "dosen")

                if (response.isSuccessful && response.body()?.status == "success") {
                    val rawData = response.body()!!.data

                    val mappedList = rawData.map { it.toDosen() }

                    dosenList.clear()
                    dosenList.addAll(mappedList)
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun toggleStatusDosen(context: Context, id: Int) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.toggleStatusUser(token, id)

                if (response.isSuccessful) {
                    android.widget.Toast.makeText(context, "Status berhasil diubah!", android.widget.Toast.LENGTH_SHORT).show()
                    getDosen(context)
                } else {
                    val errorBody = response.errorBody()?.string()
                    android.widget.Toast.makeText(context, "Gagal: ${response.code()} - $errorBody", android.widget.Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}