package com.endrazhafir.siamobile.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endrazhafir.siamobile.data.AddDosenRequest
import com.endrazhafir.siamobile.data.AddMahasiswaRequest
import com.endrazhafir.siamobile.data.Dosen
import com.endrazhafir.siamobile.data.Mahasiswa
import com.endrazhafir.siamobile.data.MataKuliah
import com.endrazhafir.siamobile.data.Program
import com.endrazhafir.siamobile.data.remote.RetrofitClient
import com.endrazhafir.siamobile.ui.components.ToastManager
import com.endrazhafir.siamobile.ui.components.ToastType
import com.endrazhafir.siamobile.utils.SessionManager
import kotlinx.coroutines.launch

class StatsViewModel : ViewModel() {

    var mahasiswaList = mutableStateListOf<Mahasiswa>()
        private set

    var mataKuliahList = mutableStateListOf<MataKuliah>()
        private set

    var dosenList = mutableStateListOf<Dosen>()
        private set

    var programsList = mutableStateListOf<Program>()
        private set

    var isLoading = mutableStateOf(false)

    fun addMahasiswa(context : Context, request: AddMahasiswaRequest) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.addMahasiswa(token, request)
                if (response.isSuccessful) {
                    ToastManager.show("Mahasiswa berhasil ditambahkan.", ToastType.SUCCESS)
                    getMahasiswa(context)
                } else {
                    val errorBody = response.errorBody()?.string()
                    ToastManager.show("Gagal menambahkan: ${response.code()} - $errorBody", ToastType.ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ToastManager.show("Terjadi kesalahan: ${e.message}", ToastType.ERROR)
            }
        }
    }

    fun getMahasiswa(context: Context) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = RetrofitClient.instance.getUsersByRole(token, "mahasiswa")

                if (response.isSuccessful && response.body()?.status == "success") {
                    val rawData = response.body()!!.data

                    val mappedList = rawData.map { it.toMahasiswa() }

                    mahasiswaList.clear()
                    mahasiswaList.addAll(mappedList)
                } else {
                    ToastManager.show("Gagal memuat data: ${response.code()}", ToastType.ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ToastManager.show("Terjadi kesalahan: ${e.message}", ToastType.ERROR)
            } finally {
                isLoading.value = false
            }
        }
    }

    fun toggleStatusMahasiswa(context: Context, id: Int) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.toggleStatusUser(token, id)

                if (response.isSuccessful) {
                    ToastManager.show("Status berhasil diubah.", ToastType.SUCCESS)
                    getMahasiswa(context)
                } else {
                    val errorBody = response.errorBody()?.string()
                    ToastManager.show("Gagal: ${response.code()} - $errorBody", ToastType.ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ToastManager.show("Terjadi kesalahan: ${e.message}", ToastType.ERROR)
            }
        }
    }

    fun addMataKuliah(context: Context, matkul: MataKuliah) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.addMataKuliah(token, matkul)
                if (response.isSuccessful) {
                    ToastManager.show("Mata kuliah berhasil ditambahkan.", ToastType.SUCCESS)
                    getMataKuliah(context)
                } else {
                    val errorBody = response.errorBody()?.string()
                    ToastManager.show("Gagal menambahkan mata kuliah: ${response.code()} - $errorBody", ToastType.ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ToastManager.show("Terjadi kesalahan: ${e.message}", ToastType.ERROR)
            }
        }
    }

    fun getMataKuliah(context: Context) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            isLoading.value = true
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
                    ToastManager.show("Gagal memuat data: ${response.code()}", ToastType.ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ToastManager.show("Terjadi kesalahan: ${e.message}", ToastType.ERROR)
            } finally {
                isLoading.value = false
            }
        }
    }

    fun updateMataKuliah(context: Context, id: Int, matkul: MataKuliah) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.updateMataKuliah(token, id, matkul)
                if (response.isSuccessful) {
                    ToastManager.show("Mata kuliah berhasil diupdate.", ToastType.SUCCESS)
                    getMataKuliah(context)
                } else {
                    val errorBody = response.errorBody()?.string()
                    ToastManager.show("Gagal mengupdate mata kuliah: ${response.code()} - $errorBody", ToastType.ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ToastManager.show("Terjadi kesalahan: ${e.message}", ToastType.ERROR)
            }
        }
    }

    fun deleteMataKuliah(context: Context, id: Int) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.deleteMataKuliah(token, id)
                if (response.isSuccessful) {
                    ToastManager.show("Mata kuliah berhasil dihapus.", ToastType.SUCCESS)
                    getMataKuliah(context)
                } else {
                    ToastManager.show("Gagal menghapus mata kuliah: ${response.code()}. Mata Kuliah sedang digunakan.", ToastType.ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ToastManager.show("Terjadi kesalahan: ${e.message}", ToastType.ERROR)
            }
        }
    }

    fun addDosen(context : Context, request: AddDosenRequest) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.addDosen(token, request)
                if (response.isSuccessful) {
                    ToastManager.show("Dosen berhasil ditambahkan.", ToastType.SUCCESS)
                    getDosen(context)
                } else {
                    val errorBody = response.errorBody()?.string()
                    ToastManager.show("Gagal menambahkan dosen: ${response.code()} - $errorBody", ToastType.ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ToastManager.show("Terjadi kesalahan: ${e.message}", ToastType.ERROR)
            }
        }
    }

    fun getDosen(context: Context) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = RetrofitClient.instance.getUsersByRole(token, "dosen")

                if (response.isSuccessful && response.body()?.status == "success") {
                    val rawData = response.body()!!.data

                    val mappedList = rawData.map { it.toDosen() }

                    dosenList.clear()
                    dosenList.addAll(mappedList)
                } else {
                    ToastManager.show("Gagal memuat data: ${response.code()}", ToastType.ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ToastManager.show("Terjadi kesalahan: ${e.message}", ToastType.ERROR)
            } finally {
                isLoading.value = false
            }
        }
    }

    fun toggleStatusDosen(context: Context, id: Int) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.toggleStatusUser(token, id)

                if (response.isSuccessful) {
                    ToastManager.show("Status berhasil diubah.", ToastType.SUCCESS)
                    getDosen(context)
                } else {
                    val errorBody = response.errorBody()?.string()
                    ToastManager.show("Gagal: ${response.code()} - $errorBody", ToastType.ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ToastManager.show("Terjadi kesalahan: ${e.message}", ToastType.ERROR)
            }
        }
    }

    fun getPrograms(context: Context) {
        val token = SessionManager(context).getToken() ?: return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getPrograms(token)
                if (response.isSuccessful && response.body()?.status == "success") {
                    programsList.clear()
                    programsList.addAll(response.body()!!.data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ToastManager.show("Terjadi kesalahan: ${e.message}", ToastType.ERROR)
            }
        }
    }
}