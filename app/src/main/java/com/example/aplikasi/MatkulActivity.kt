package com.example.aplikasi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasi.databinding.ActivityMatkulBinding

class MatkulActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatkulBinding
    private lateinit var dosenLihatAdapter: DosenLihatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatkulBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
