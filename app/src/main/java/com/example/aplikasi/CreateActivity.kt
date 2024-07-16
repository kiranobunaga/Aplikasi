package com.example.aplikasi

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplikasi.databinding.ActivityCreateBinding
import com.google.firebase.firestore.FirebaseFirestore

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    private val db = FirebaseFirestore.getInstance()
    private val dosenCollection = db.collection("dosen")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTambah.setOnClickListener {
            val nama = binding.etNama.text.toString()
            val nip = binding.etNIP.text.toString()
            val matkul = binding.etMatkul.text.toString()
            val umur = binding.etUmur.text.toString()

            // Input validation (add more specific checks as needed)
            if (nama.isBlank()) {
                binding.etNama.error = "Nama tidak boleh kosong"
                return@setOnClickListener
            }


            val umurInt = try {
                umur.toInt()
            } catch (e: NumberFormatException) {
                binding.etUmur.error = "Umur harus berupa angka"
                return@setOnClickListener
            }

            val newDosen = Dosen(nama, nip, matkul, umurInt)
            createDosen(newDosen)
        }
    }

    private fun createDosen(dosen: Dosen) {
        // Show progress indicator (e.g., ProgressBar)
        binding.progressBar.visibility = View.VISIBLE
        binding.btnTambah.isEnabled = false // Disable button

        dosenCollection.add(dosen)
            .addOnSuccessListener {
                Toast.makeText(this, "Dosen ditambahkan", Toast.LENGTH_SHORT).show()
                finish() // Close the activity
            }
            .addOnFailureListener { e ->
                Log.e("CreateActivity", "Gagal menambahkan dosen", e)
                Toast.makeText(this, "Gagal menambahkan dosen: ${e.message}", Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener {
                binding.progressBar.visibility = View.GONE
                binding.btnTambah.isEnabled = true
            }
    }

    data class Dosen(val nama: String, val nip: String, val matkul: String, val umur: Int)
}