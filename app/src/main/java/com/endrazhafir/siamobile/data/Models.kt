package com.endrazhafir.siamobile.data

import com.google.gson.annotations.SerializedName
import javax.security.auth.Subject

// Data yg dikirim pas Login
data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

// Data di dalam "data": { ... } respon Login
data class LoginData(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("user") val user: UserProfile
)

// 3. Detail User
data class UserProfile(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("roles") val roles: List<String>
)

data class Profile(
    @SerializedName("id_user_si") val id: Int,
    @SerializedName("role") val role: String,
    @SerializedName("is_active") val isActive: Boolean,
)

data class Mahasiswa(
    @SerializedName("id_user_si") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("nim") val nim: String,
    @SerializedName("program_name") val programName: String,
    @SerializedName("is_active") val isActive: Boolean,
)

data class MataKuliah(
    @SerializedName("id_subject") val id: Int,
    @SerializedName("name_subject") val nameSubject: String,
    @SerializedName("code_subject") val codeSubject: String,
    @SerializedName("sks") val sks: Int,
)

data class Dosen(
    @SerializedName("id_user_si") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("program_name") val programName: String,
    @SerializedName("is_active") val isActive: Boolean,
)

