package com.example.appcafecol

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FincaAdapter(private val fincas: List<Finca>, private val clickListener: (Finca) -> Unit) :
    RecyclerView.Adapter<FincaAdapter.FincaViewHolder>() {

    class FincaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fincaNombreTextView: TextView = itemView.findViewById(R.id.fincaNombreTextView)
        val chargeTextView: TextView = itemView.findViewById(R.id.cargoTextView)
        val availabilityTextView: TextView = itemView.findViewById(R.id.disponibilidadTextView)
        val locationTextView: TextView = itemView.findViewById(R.id.ubicacionTextView)
        val salaryTextView: TextView = itemView.findViewById(R.id.salarioTextView)
        val workTextView: TextView = itemView.findViewById(R.id.trabajoTextView)
        val experienceTextView: TextView = itemView.findViewById(R.id.experienciaTextView)
        val tiempoTextView: TextView = itemView.findViewById(R.id.tiempoTextView)

        fun bind(finca: Finca, clickListener: (Finca) -> Unit) {
            fincaNombreTextView.text = finca.finca
            chargeTextView.text = finca.charge
            availabilityTextView.text = finca.availability
            locationTextView.text = finca.location
            salaryTextView.text = finca.salary
            workTextView.text = finca.work
            experienceTextView.text = finca.experience

            val dateCreated = finca.dateCreated.toDate().time
            tiempoTextView.text = "Creado hace: ${Utils().getTimeAgo(dateCreated)}"

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





