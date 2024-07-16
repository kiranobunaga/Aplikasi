package com.example.aplikasi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasi.databinding.ActivityLihatDosenBinding
import com.google.firebase.firestore.FirebaseFirestore

class LihatDosenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLihatDosenBinding
    private val db = FirebaseFirestore.getInstance()
    private val dosenCollection = db.collection("dosen")
    private lateinit var dosenAdapter: DosenLihatAdapter // Use DosenLihatAdapter instead of MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLihatDosenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        dosenAdapter = DosenLihatAdapter(emptyList()) // Initialize DosenLihatAdapter with an empty list
        binding.dosenRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@LihatDosenActivity)
            adapter = dosenAdapter
        }

        loadDosenData()
    }

    private fun loadDosenData() {
        dosenCollection.get()
            .addOnSuccessListener { documents ->
                val dosenList = documents.mapNotNull { it.toObject(Dosen::class.java) }
                dosenAdapter.updateDosenList(dosenList)
            }
            .addOnFailureListener { e ->
                // Handle errors
            }
    }
}
