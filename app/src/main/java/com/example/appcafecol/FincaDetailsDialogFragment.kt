package com.example.appcafecol

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FincaDetailsDialogFragment : DialogFragment() {

    private lateinit var finca: Finca
    private val db = FirebaseFirestore.getInstance()

    companion object {
        private const val ARG_FINC = "finca"

        fun newInstance(finca: Finca): FincaDetailsDialogFragment {
            val fragment = FincaDetailsDialogFragment()
            val args = Bundle()
            args.putParcelable(ARG_FINC, finca)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            finca = it.getParcelable(ARG_FINC)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_finca_details_dialog, container, false)

        view.findViewById<TextView>(R.id.fincaNombreTextView).text = finca.finca
        view.findViewById<TextView>(R.id.cargoTextView).text = finca.charge
        view.findViewById<TextView>(R.id.disponibilidadTextView).text = finca.availability
        view.findViewById<TextView>(R.id.ubicacionTextView).text = finca.location
        view.findViewById<TextView>(R.id.salarioTextView).text = finca.salary
        view.findViewById<TextView>(R.id.trabajoTextView).text = finca.work
        view.findViewById<TextView>(R.id.experienciaTextView).text = finca.experience

        // Manejo del botón de postulación
        view.findViewById<Button>(R.id.apply_button).setOnClickListener {
            applyForFinca()
        }

        return view
    }

    private fun applyForFinca() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val applicantEmail = currentUser?.email ?: "applicant@example.com" // Obtener el correo del usuario actual
        val ownerEmail = finca.ownerEmail // Obtener el correo del dueño de la finca
        val messageToOwner = "Hola, alguien se ha postulado a tu oferta para ${finca.finca}."
        val messageToApplicant = "Te has postulado exitosamente a la oferta de ${finca.finca}."

        // Agregar notificación para el dueño de la finca
        addNotification(ownerEmail, messageToOwner, applicantEmail)

        // Agregar notificación para el solicitante (opcional)
        // addNotification(applicantEmail, messageToApplicant, applicantEmail)

        Toast.makeText(context, "Te has postulado correctamente.", Toast.LENGTH_SHORT).show()
    }

    private fun addNotification(userEmail: String, message: String, applicantEmail: String) {
        val notification = Notification(message = message, timestamp = System.currentTimeMillis(), userEmail = applicantEmail)
        db.collection("notifications")
            .add(mapOf(
                "userEmail" to userEmail,
                "message" to notification.message,
                "timestamp" to notification.timestamp,
                "applicantEmail" to notification.userEmail
            ))
            .addOnSuccessListener {
                // Notificación agregada correctamente
                Log.d("Notification", "Notificación guardada: ${notification.message}, ${notification.userEmail}")
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}