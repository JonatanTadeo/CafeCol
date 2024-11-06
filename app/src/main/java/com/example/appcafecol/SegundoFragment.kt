package com.example.appcafecol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class SegundoFragment : Fragment() {

    private lateinit var fincaAdapter: FincaAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_segundo, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewFinca)
        recyclerView.layoutManager = LinearLayoutManager(context)

        loadFincas { fincas ->
            fincaAdapter = FincaAdapter(fincas) { finca ->
                showFincaDetails(finca)
            }
            recyclerView.adapter = fincaAdapter
        }

        return view
    }

    private fun loadFincas(callback: (List<Finca>) -> Unit) {
        db.collection("farms")
            .get()
            .addOnSuccessListener { result ->
                val fincas = result.map { document ->
                    document.toObject(Finca::class.java)
                }
                callback(fincas)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    private fun showFincaDetails(finca: Finca) {
        val fragment = FincaDetailsDialogFragment.newInstance(finca)
        fragment.show(parentFragmentManager, "FincaDetailsDialog")
    }
}