package com.example.appcafecol

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PerfilesAdapter(private val perfiles: List<Perfil>, private val clickListener: (Perfil) -> Unit) :
    RecyclerView.Adapter<PerfilesAdapter.PerfilViewHolder>() {

    // Crear una instancia de Utils
    private val utils = Utils()

    class PerfilViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreTextView)
        val edadTextView: TextView = itemView.findViewById(R.id.edadTextView)
        val correoTextView: TextView = itemView.findViewById(R.id.correoTextView)
        val numeroTextView: TextView = itemView.findViewById(R.id.numeroTextView)
        val ubicacionTextView: TextView = itemView.findViewById(R.id.ubicacionTextView)
        val tiempoTextView: TextView = itemView.findViewById(R.id.tiempoTextView)

        fun bind(perfil: Perfil, clickListener: (Perfil) -> Unit, utils: Utils) {
            nombreTextView.text = perfil.nombre
            edadTextView.text = perfil.edad
            correoTextView.text = perfil.correo
            numeroTextView.text = perfil.numero
            ubicacionTextView.text = perfil.ubicacion

            val dateCreated = perfil.dateCreated.toDate().time
            tiempoTextView.text = "Creado hace: ${utils.getTimeAgo(dateCreated)}"

            itemView.setOnClickListener { clickListener(perfil) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfilViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_perfil, parent, false)
        return PerfilViewHolder(view)
    }

    override fun onBindViewHolder(holder: PerfilViewHolder, position: Int) {
        holder.bind(perfiles[position], clickListener, utils)
    }

    override fun getItemCount(): Int {
        return perfiles.size
    }
}
