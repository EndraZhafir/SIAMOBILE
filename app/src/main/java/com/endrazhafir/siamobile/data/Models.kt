package com.endrazhafir.siamobile.data

import com.google.gson.annotations.SerializedName

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

// Detail User
data class UserProfile(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("roles") val roles: List<String>
)

// Data di dalam "data": { ... } respon Mahasiswa & Dosen
data class UserResponse(
    @SerializedName("id_user_si") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("nim") val nim: String?,
    @SerializedName("program_name") val programName: String?,
    @SerializedName("is_active") val isActive: Boolean
) {
    fun toMahasiswa(): Mahasiswa {
        return Mahasiswa(
            id = id,
            nameStudent = name,
            email = email,
            username = username,
            nim = nim ?: "-",
            programName = programName ?: "-",
            isActive = isActive
        )
    }

    fun toDosen(): Dosen {
        return Dosen(
            id = id,
            nameLecturer = name,
            email = email,
            username = username,
            programName = programName ?: "-",
            isActive = isActive
        )
    }
}

data class Mahasiswa(
    val id: Int,
    val nameStudent: String,
    val email: String,
    val username: String,
    val nim: String,
    val programName: String,
    val isActive: Boolean
)

data class Dosen(
    val id: Int,
    val nameLecturer: String,
    val email: String,
    val username: String,
    val programName: String,
    val isActive: Boolean
)

// Data yg dikirim saat tambah Mahasiswa
data class AddMahasiswaRequest(
    @SerializedName("name")
    val nameStudent: String,
    val username: String,
    val email: String,
    val password: String,
    @SerializedName("password_confirmation") val passwordConfirmation: String,
    @SerializedName("id_program") val idProgram: Int,
    @SerializedName("registration_number") val nim: String
)

// Data yg dikirim saat tambah Dosen
data class AddDosenRequest(
    @SerializedName("name")
    val nameLecturer: String,
    val username: String,
    val email: String,
    val password: String,
    @SerializedName("password_confirmation") val passwordConfirmation: String,
    @SerializedName("id_program") val idProgram: Int,
)
data class MataKuliah(
    @SerializedName("id_subject") val id: Int,
    @SerializedName("name_subject") val nameSubject: String,
    @SerializedName("code_subject") val codeSubject: String,
    @SerializedName("sks") val sks: Int,
)

// Data prodi
data class Program(
    @SerializedName("id_program") val id: Int,
    @SerializedName("name") val name: String
)
