# SIAMOBILE (Sistem Informasi Akademik) 🎓

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=android&logoColor=white)
![Laravel](https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white)

Aplikasi Android native untuk manajemen data akademik (Mahasiswa, Dosen, Mata Kuliah) Universitas Global Nusantara. Aplikasi ini dibangun menggunakan **Kotlin** & **Jetpack Compose** dengan arsitektur **MVVM**, terintegrasi dengan backend **Laravel API**.

### 📌 Tentang Proyek
Proyek ini dikembangkan sebagai **Tugas Akhir** mata kuliah **Praktikum Pemrograman Perangkat Bergerak (Pemrograman Aplikasi Mobile)**. SIAMOBILE merupakan adaptasi mobile dari modul manajemen admin pada proyek web SIA UGN (Mata Kuliah Pengembangan Aplikasi Dasar). Aplikasi ini bertujuan untuk mempermudah administrator dalam mengelola data akademik secara *real-time* dan efisien melalui perangkat mobile.

**Tim Pengembang (Kelompok 21):**
* **Muhammad Fauzan Putra Trisuladana** (24/538699/SV/24524)
* **Tb. Muhammad Endra Zhafir Al Ghifari** (24/538769/SV/24535)
* **Prihastomo Budi Satrio** (24/534908/SV/24108)
* **Devin Sotya Prathama** (24/542213/SV/25017)

## 📱 Screenshots UI Aplikasi

<p align="center">
  <img width="150" alt="screenshot_login" src="https://github.com/user-attachments/assets/5f8bdbe7-1b7c-48f9-ac8d-97a1c91187cb" />
  <img width="150" alt="screenshot_dashboard" src="https://github.com/user-attachments/assets/41150242-1e06-4327-be00-d0571ab4602e" />
  <img width="150" alt="screenshot_stats" src="https://github.com/user-attachments/assets/1cfd1f20-03f1-4878-9f3c-89d12d46a829" />
  <img width="150" alt="screenshot_form" src="https://github.com/user-attachments/assets/f675c53e-2ae0-4e20-a786-3d27b1eb154e" />
  <img width="150" alt="screenshot_toast" src="https://github.com/user-attachments/assets/88021d4b-5deb-453d-9069-f0a7cb481060" />
</p>

**Keterangan Tampilan:**
1.  **Halaman Login:** Desain modern dengan validasi input dan autentikasi token JWT.
2.  **Dashboard Admin:** Menampilkan ringkasan statistik (jumlah mahasiswa, dosen, matkul aktif) secara *real-time* dengan fitur *Pull-to-Refresh*.
3.  **List Manajemen Data:** Tabel data responsif dengan fitur pencarian cepat (*live search*), pagination, dan status indikator aktif/non-aktif.
4.  **Form Input (Bottom Sheet):** Form tambah/edit data menggunakan *Modal Bottom Sheet* yang dinamis, dilengkapi validasi ketat dan *Searchable Dropdown* untuk program studi.
5.  **Custom Notifications:** Sistem notifikasi *Toast* kustom yang muncul dari atas layar (mirip gaya notifikasi modern) untuk memberikan umpan balik sukses atau error yang jelas kepada pengguna.

## ✨ Fitur Unggulan

* **🔐 Secure Authentication:** Login aman menggunakan JWT Token dengan mekanisme *Session Manager* dan validasi Role (Admin/Manager).
* **📊 Real-time Dashboard:** Monitoring statistik akademik yang selalu terbarui.
* **🛠️ Comprehensive CRUD:**
    * **Tambah**, **Edit**, dan **Hapus** data Mahasiswa, Dosen, & Mata Kuliah.
    * **Status Toggle:** Mengaktifkan atau menonaktifkan akun pengguna.
    * **Validation:** Pencegahan input duplikat (NIM/Email) dengan pesan error yang informatif dari server.
* **⚡ Modern UX/UI:**
    * **Jetpack Compose:** UI deklaratif yang smooth dan responsif.
    * **Edge-to-Edge Design:** Tampilan layar penuh yang imersif.
    * **Loading States:** Indikator visual saat proses pengambilan data.

## 🛠️ Tech Stack

* **Language:** Kotlin
* **UI Toolkit:** Jetpack Compose (Material3)
* **Architecture:** MVVM (Model-View-ViewModel)
* **Networking:** Retrofit2 + OkHttp + GSON
* **Asynchronous:** Kotlin Coroutines & Flow
* **Backend Integration:** Laravel 12 (REST API)

## 📥 Download

Versi terbaru aplikasi (APK) dapat diunduh melalui menu **[Releases](../../releases)**.

## 🚀 Panduan Instalasi (Developer)

Jika Anda ingin menjalankan source code ini di mesin lokal:

1.  **Clone Repository ini:**
    Buka terminal atau Git Bash, lalu ketik perintah berikut untuk mengunduh kode sumber ke komputermu:

    ```bash
    git clone https://github.com/username/SIAMOBILE.git
    ```
    
2.  **Buka di Android Studio:** Gunakan versi terbaru seperti Otter 2 Feature Drop atau versi yang lebih baru.
3.  **Konfigurasi API:** Buka file `RetrofitClient.kt` dan sesuaikan `BASE_URL`:
   
    ```kotlin
    // Untuk Server Online (Default)
    private const val BASE_URL = "https://api.trisuladana.com/api/"

    // Untuk Server Lokal (Emulator)
    private const val BASE_URL = "http://10.0.2.2:8000/api/"
    ```
    
5.  **Sync & Run:** Lakukan Sync Gradle dan jalankan aplikasi di Emulator atau Device Anda.

---
Developed with ❤️ by **Averone30 (Endra Zhafir)**.
