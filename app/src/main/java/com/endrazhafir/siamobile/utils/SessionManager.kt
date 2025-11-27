package com.endrazhafir.siamobile.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        const val KEY_TOKEN = "auth_token"
        const val KEY_IS_LOGIN = "is_login"

        const val KEY_USER_NAME = "user_name"
    }

    // Simpan Token
    fun saveAuthToken(token: String, name: String) {
        prefs.edit {
            putString(KEY_TOKEN, token)
            putString(KEY_USER_NAME, name)
            putBoolean(KEY_IS_LOGIN, true)
        }
    }

    // Ambil Token
    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    fun getUserName(): String? {
        return prefs.getString(KEY_USER_NAME, "Admin")
    }

    // Cek apakah user sudah login
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGIN, false)
    }

    // Logout
    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}
