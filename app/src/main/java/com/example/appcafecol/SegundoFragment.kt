package com.example.appcafecol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class SegundoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FincaAdapter
    private var fincaList = mutableListOf<Finca>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_segundo, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewFinca)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FincaAdapter(fincaList) { finca -> showFincaDetails(finca) }
        recyclerView.adapter = adapter

        loadFincas()

        return view
    }

    private fun loadFincas() {
        val db = FirebaseFirestore.getInstance()
        db.collection("farms")
            .get()
            .addOnSuccessListener { result ->
                fincaList.clear()
                for (document in result) {
                    val finca = document.toObject(Finca::class.java)
                    fincaList.add(finca)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error al cargar fincas: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showFincaDetails(finca: Finca) {
        val dialog = FincaDetailsDialogFragment.newInstance(finca)
        dialog.show(parentFragmentManager, "FincaDetailsDialog")
    }

    override fun onResume() {
        super.onResume()
        loadFincas()
    }
}


