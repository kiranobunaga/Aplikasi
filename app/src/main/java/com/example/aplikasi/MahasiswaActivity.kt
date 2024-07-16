package com.example.aplikasi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasi.databinding.ActivityMahasiswaBinding
import com.google.firebase.auth.FirebaseAuth

class MahasiswaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMahasiswaBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMahasiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        setupViews()

        binding.btnDosen.setOnClickListener {
            startActivity(Intent(this, LihatDosenActivity::class.java))
        }



        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            navigateToLoginActivity()
        }
    }

    private fun setupViews() {
        // No specific views to set up in this example
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
