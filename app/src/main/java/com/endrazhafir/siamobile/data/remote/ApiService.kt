package com.endrazhafir.siamobile.data.remote

import com.endrazhafir.siamobile.data.AddDosenRequest
import com.endrazhafir.siamobile.data.AddMahasiswaRequest
import com.endrazhafir.siamobile.data.LoginData
import com.endrazhafir.siamobile.data.LoginRequest
import com.endrazhafir.siamobile.data.MataKuliah
import com.endrazhafir.siamobile.data.Program
import com.endrazhafir.siamobile.data.UserResponse
import com.endrazhafir.siamobile.data.response.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // --- AUTH LOGIN ---
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<ApiResponse<LoginData>>

    // --- MAHASISWA ---
    // POST /api/manager/students
    @POST("manager/students")
    suspend fun addMahasiswa(
        @Header("Authorization") token: String,
        // Kita butuh Body Request khusus karena field-nya beda sama Model Mahasiswa (misal password)
        @Body request: AddMahasiswaRequest
    ): Response<ApiResponse<Any>> // Data balikan bisa Any atau Mahasiswa

    // --- MATA KULIAH ---
    // GET /api/manager/subjects
    @GET("manager/subjects")
    suspend fun getAllMataKuliah(
        @Header("Authorization") token: String
    ): Response<ApiResponse<List<MataKuliah>>>

    // POST /api/manager/subjects
    @POST("manager/subjects")
    suspend fun addMataKuliah(
        @Header("Authorization") token: String,
        @Body matkul: MataKuliah
    ): Response<ApiResponse<MataKuliah>>

    // PUT /api/manager/subjects/{id}
    @PUT("manager/subjects/{id}")
    suspend fun updateMataKuliah(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body matkul: MataKuliah
    ): Response<ApiResponse<MataKuliah>>

    // DELETE /api/manager/subjects/{id}
    @DELETE("manager/subjects/{id}")
    suspend fun deleteMataKuliah(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<ApiResponse<Any>>

    // --- DOSEN ---
    // POST /api/manager/lecturers
    @POST("manager/lecturers")
    suspend fun addDosen(
        @Header("Authorization") token: String,
        @Body request: AddDosenRequest
    ): Response<ApiResponse<Any>>

    // MAHASISWA & DOSEN
    // GET /api/manager/users-by-role?role=mahasiswa
    // GET /api/manager/users-by-role?role=dosen
    @GET("manager/users-by-role")
    suspend fun getUsersByRole(
        @Header("Authorization") token: String,
        @Query("role") role: String
    ): Response<ApiResponse<List<UserResponse>>>

    // PATCH /api/manager/users/{id}/toggle-status
    @PATCH("manager/users/{id}/toggle-status")
    suspend fun toggleStatusUser(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<ApiResponse<Any>>

    // --- TAMBAHAN ---
    @GET("manager/programs")
    suspend fun getPrograms(
        @Header("Authorization") token: String
    ): Response<ApiResponse<List<Program>>>
}