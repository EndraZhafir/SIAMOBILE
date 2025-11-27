package com.endrazhafir.siamobile.data.remote

import com.endrazhafir.siamobile.data.LoginData
import com.endrazhafir.siamobile.data.LoginRequest
import com.endrazhafir.siamobile.data.MataKuliah
import com.endrazhafir.siamobile.data.response.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // --- AUTH LOGIN ---
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<ApiResponse<LoginData>>

    // --- MATA KULIAH (Sesuai SubjectController) ---

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
}