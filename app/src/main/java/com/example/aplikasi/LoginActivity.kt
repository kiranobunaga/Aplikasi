package com.example.aplikasi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasi.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Check if user is already logged in
        auth.currentUser?.let { updateUI(it) } // If user exists, navigate directly

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim() // Trim whitespace
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.etEmail.error = "Email Harus Diisi"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.etPassword.error = "Password Harus Diisi"
                return@setOnClickListener
            }

            loginFirebase(email, password)
        }
    }

    private fun loginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    updateUI(auth.currentUser)
                } else {
                    Toast.makeText(this, "Email atau Password anda salah", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            db.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val role = document.getString("role")
                        val intent = when (role) {
                            "mahasiswa" -> Intent(this, MahasiswaActivity::class.java)
                            "dosen" -> Intent(this, DosenActivity::class.java)
                            else -> {
                                Toast.makeText(this, "Role Anda Belum Ditentukan", Toast.LENGTH_SHORT).show()
                                null // No intent if role is not recognized
                            }
                        }
                        intent?.let { startActivity(it) }
                        finish()
                    } else {
                        Toast.makeText(this, "Document tidak ditemukan.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("LoginActivity", "Error getting document: ", exception)
                    Toast.makeText(this, "Gagal mendapatkan data user", Toast.LENGTH_SHORT).show()
                }
        }
    }
}