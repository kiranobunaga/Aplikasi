package com.example.aplikasi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasi.databinding.ActivityDosenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException

class DosenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDosenBinding
    private val db = FirebaseFirestore.getInstance()
    private val dosenCollection = db.collection("dosen")
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dosenAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDosenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        dosenAdapter = MyAdapter(emptyList(),
            onEditClickListener = { dosen -> editDosen(dosen) },
            onDeleteClickListener = { dosen -> deleteDosen(dosen) }
        )

        binding.dosenRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@DosenActivity)
            adapter = dosenAdapter
        }

        Log.d("DosenActivity", "onCreate: RecyclerView and Adapter set up")

        readDosenData()

        binding.btnTambah.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            navigateToLoginActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        readDosenData()
    }

    private fun readDosenData() {
        dosenCollection.get()
            .addOnSuccessListener { documents ->
                val dosenList = documents.mapNotNull { doc ->
                    val dosen = doc.toObject(Dosen::class.java).copy(id = doc.id)
                    dosen
                }
                dosenAdapter.updateDosenList(dosenList)
            }
            .addOnFailureListener { e ->
                Log.e("DosenActivity", "Error reading dosen data: ", e)
                Toast.makeText(this, "Failed to read dosen data", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteDosen(dosen: Dosen) {
        dosen.id?.let {
            dosenCollection.document(it)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Dosen deleted successfully", Toast.LENGTH_SHORT).show()
                    readDosenData() // Refresh the list after deletion
                }
                .addOnFailureListener { e ->
                    Log.w("DosenActivity", "Error deleting document", e)
                    Toast.makeText(this, "Failed to delete dosen", Toast.LENGTH_SHORT).show()
                }
        }
    }


    private fun editDosen(dosen: Dosen) {
        val intent = Intent(this, EditDosenActivity::class.java)
        intent.putExtra("dosen", dosen)
        startActivity(intent)
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}