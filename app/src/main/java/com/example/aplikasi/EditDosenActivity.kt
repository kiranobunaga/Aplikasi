package com.example.aplikasi

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasi.databinding.ActivityEditDosenBinding
import com.google.firebase.firestore.FirebaseFirestore

class EditDosenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditDosenBinding
    private lateinit var dosen: Dosen
    private val db = FirebaseFirestore.getInstance()
    private val dosenCollection = db.collection("dosen")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDosenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dosen = intent.getParcelableExtra("dosen") ?: return
        populateFields()

        binding.btnSave.setOnClickListener {
            saveDosen()
        }
    }

    private fun populateFields() {
        binding.etNamaDosen.setText(dosen.nama)
        binding.etNIP.setText(dosen.nip)
        binding.etMatkul.setText(dosen.matkul)
    }

    private fun saveDosen() {
        val updatedDosen = Dosen(
            id = dosen.id,
            nama = binding.etNamaDosen.text.toString(),
            nip = binding.etNIP.text.toString(),
            matkul = binding.etMatkul.text.toString()
        )

        dosen.id?.let {
            dosenCollection.document(it)
                .set(updatedDosen)
                .addOnSuccessListener {
                    Toast.makeText(this, "Dosen updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.e("EditDosenActivity", "Error updating dosen: ", e)
                    Toast.makeText(this, "Failed to update dosen", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
