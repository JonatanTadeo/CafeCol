package com.example.appcafecol

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcafecol.databinding.FragmentQuintoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class QuintoFragment : Fragment() {

    private lateinit var binding: FragmentQuintoBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var notificationAdapter: NotificationAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuintoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        // Configuración del RecyclerView
        val recyclerView = binding.recyclerViewNotifications
        recyclerView.layoutManager = LinearLayoutManager(context)

        loadNotifications { notifications ->
            notificationAdapter = NotificationAdapter(notifications)
            recyclerView.adapter = notificationAdapter
        }

        // Cerrar sesión y volver a MainActivity
        binding.CerrarSesionAdmin.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finishAffinity()
        }
    }

    private fun loadNotifications(callback: (List<Notification>) -> Unit) {
        val currentUser = firebaseAuth.currentUser
        currentUser?.email?.let { email ->
            db.collection("notifications")
                .whereEqualTo("userEmail", email)
                .get()
                .addOnSuccessListener { result ->
                    val notifications = result.map { document ->
                        document.toObject(Notification::class.java)
                    }
                    callback(notifications)
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }
}



