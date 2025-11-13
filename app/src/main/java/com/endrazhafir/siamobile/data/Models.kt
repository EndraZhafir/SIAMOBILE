package com.endrazhafir.siamobile.data

data class Mahasiswa(
    val id: Int,
    val username: String,
    val email: String,
    val nama: String,
    val nim: String,
    val program: String,
    val status: String = "Active"
)

data class MataKuliah(
    val id: Int,
    val nama: String,
    val kode: String,
    val sks: Int,
)

data class Dosen(
    val id: Int,
    val username: String,
    val email: String,
    val nama: String,
    val program: String,
    val status: String = "Active"
)
