package com.example.appcafecol

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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
            notificationAdapter = NotificationAdapter(notifications) { notification ->
                showNotificationDetails(notification)
            }
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
                        val notification = document.toObject(Notification::class.java)
                        Log.d("Notification", "Notificación recuperada: ${notification.message}, ${notification.userEmail}")
                        notification
                    }
                    callback(notifications)
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }

    private fun showNotificationDetails(notification: Notification) {
        val fragment = NotificationDetailsDialogFragment.newInstance(notification)
        fragment.show(parentFragmentManager, "NotificationDetailsDialog")
    }
}




