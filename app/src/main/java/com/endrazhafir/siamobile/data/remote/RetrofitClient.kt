package com.endrazhafir.siamobile.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    /**
     * Sesuaikan Port Laravel.
     * Kalo 'php artisan serve', biasanya port 8000 -> http://10.0.2.2:8000/api/
     * Kalo untuk online https://api.trisuladana.com/api/
     * Kalo pake HP fisik/eksternal, ganti ke IP Address Laptop (misal 192.168.1.5)
     * Jalankan Laravel dengan: php artisan serve --host 0.0.0.0 --port 8000.
     */
    private const val BASE_URL = "https://api.trisuladana.com/api/"

    val instance: ApiService by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Biar kelihatan request/response di Logcat
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS) // Tambahin timeout biar gak gampang RTO
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}