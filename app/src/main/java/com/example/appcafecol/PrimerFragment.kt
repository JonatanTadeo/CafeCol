package com.example.appcafecol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class PrimerFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PerfilesAdapter
    private var perfilesList = mutableListOf<Perfil>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_primer, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PerfilesAdapter(perfilesList) { perfil -> showPerfilDetails(perfil) }
        recyclerView.adapter = adapter

        loadPerfiles()

        return view
    }

    private fun loadPerfiles() {
        val db = FirebaseFirestore.getInstance()
        db.collection("profiles")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val perfil = document.toObject(Perfil::class.java)
                    perfilesList.add(perfil)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Manejar error
            }
    }

    private fun showPerfilDetails(perfil: Perfil) {
        val dialog = PerfilDetailsDialogFragment.newInstance(perfil)
        dialog.show(parentFragmentManager, "PerfilDetailsDialog")
    }
}
