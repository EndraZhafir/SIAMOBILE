package com.endrazhafir.siamobile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.endrazhafir.siamobile.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        binding.btnLogin.setOnClickListener {
            handleLogin()
        }
    }

    private fun handleLogin() {
        val email = binding.email.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty()) {
            binding.email.error = "Email tidak boleh kosong"
            return
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "Password tidak boleh kosong"
            return
        }

        // TODO: Implement actual login
        Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
    }
}