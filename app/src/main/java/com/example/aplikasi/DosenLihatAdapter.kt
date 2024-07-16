package com.example.aplikasi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DosenLihatAdapter(
    private var dosenList: List<Dosen>
) : RecyclerView.Adapter<DosenLihatAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaTextView: TextView = itemView.findViewById(R.id.tvdosen)
        val nipTextView: TextView = itemView.findViewById(R.id.tvnip)
        val matkulTextView: TextView = itemView.findViewById(R.id.tvmatkul)

        init {
            Log.d("MyViewHolder", "ViewHolder initialized with itemView: $itemView")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        Log.d("DosenLihatAdapter", "onCreateViewHolder: itemView inflated")
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dosenList[position]
        holder.namaTextView.text = currentItem.nama
        holder.nipTextView.text = currentItem.nip
        holder.matkulTextView.text = currentItem.matkul

        Log.d("DosenLihatAdapter", "onBindViewHolder: Bound data for position $position")
    }

    override fun getItemCount(): Int {
        return dosenList.size
    }

    fun updateDosenList(newDosenList: List<Dosen>) {
        dosenList = newDosenList
        notifyDataSetChanged()
        Log.d("DosenLihatAdapter", "updateDosenList: List updated with ${newDosenList.size} items")
    }
}
