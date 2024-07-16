package com.example.aplikasi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private var dosenList: List<Dosen>,
    private val onEditClickListener: (Dosen) -> Unit,
    private val onDeleteClickListener: (Dosen) -> Unit
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaTextView: TextView = itemView.findViewById(R.id.tvdosen)
        val nipTextView: TextView = itemView.findViewById(R.id.tvnip)
        val matkulTextView: TextView = itemView.findViewById(R.id.tvmatkul)
        val editButton: ImageButton = itemView.findViewById(R.id.btnEdit)
        val deleteButton: ImageButton = itemView.findViewById(R.id.btnDelete)

        init {
            Log.d("MyViewHolder", "ViewHolder initialized with itemView: $itemView")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        Log.d("MyAdapter", "onCreateViewHolder: itemView inflated")
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dosenList[position]
        holder.namaTextView.text = currentItem.nama
        holder.nipTextView.text = currentItem.nip
        holder.matkulTextView.text = currentItem.matkul

        holder.editButton.setOnClickListener { onEditClickListener(currentItem) }
        holder.deleteButton.setOnClickListener { onDeleteClickListener(currentItem) }

        Log.d("MyAdapter", "onBindViewHolder: Bound data for position $position")
    }

    override fun getItemCount(): Int = dosenList.size

    fun updateDosenList(newDosenList: List<Dosen>) {
        dosenList = newDosenList
        notifyDataSetChanged()
        Log.d("MyAdapter", "updateDosenList: List updated with ${newDosenList.size} items")
    }
}