package com.example.appcafecol

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FincaAdapter(private val fincas: List<Finca>, private val clickListener: (Finca) -> Unit) :
    RecyclerView.Adapter<FincaAdapter.FincaViewHolder>() {

    class FincaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.fincaNombreTextView)
        val cargoTextView: TextView = itemView.findViewById(R.id.cargoTextView)
        val ubicacionTextView: TextView = itemView.findViewById(R.id.ubicacionTextView)

        fun bind(finca: Finca, clickListener: (Finca) -> Unit) {
            nombreTextView.text = finca.finca
            cargoTextView.text = finca.charge
            ubicacionTextView.text = finca.location

            itemView.setOnClickListener { clickListener(finca) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FincaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_finca, parent, false)
        return FincaViewHolder(view)
    }

    override fun onBindViewHolder(holder: FincaViewHolder, position: Int) {
        holder.bind(fincas[position], clickListener)
    }

    override fun getItemCount(): Int {
        return fincas.size
    }
}








