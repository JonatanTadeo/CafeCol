package com.example.appcafecol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class TercerFragment : Fragment() {

    private lateinit var etName: EditText
    private lateinit var etYears: EditText
    private lateinit var etEmail: EditText
    private lateinit var etNumber: EditText
    private lateinit var etLocation: EditText
    private lateinit var etExperience: EditText
    private lateinit var etOtherExperience: EditText
    private lateinit var saveButton: Button
    private lateinit var updateButton: Button

    private var email: String? = null
    private val utils = Utils()  // Crear instancia de Utils

    companion object {
        @JvmStatic
        fun newInstance(email: String) =
            TercerFragment().apply {
                arguments = Bundle().apply {
                    putString("email", email)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString("email")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tercer, container, false)

        etName = view.findViewById(R.id.name_edit_text)
        etYears = view.findViewById(R.id.years_edit_text)
        etEmail = view.findViewById(R.id.email_edit_text)
        etNumber = view.findViewById(R.id.number_edit_text)
        etLocation = view.findViewById(R.id.location_edit_text)
        etExperience = view.findViewById(R.id.experience_edit_text)
        etOtherExperience = view.findViewById(R.id.otherexperience_edit_text)
        saveButton = view.findViewById(R.id.save_button)
        updateButton = view.findViewById(R.id.update_button)

        saveButton.setOnClickListener {
            saveProfile()
        }

        updateButton.setOnClickListener {
            updateProfile()
        }

        email?.let { loadProfile(it) }

        return view
    }


    private fun loadProfile(email: String) {
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("profiles").document(email)

        userRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    etName.setText(document.getString("nombre"))
                    etYears.setText(document.getString("edad"))
                    etEmail.setText(document.getString("correo"))
                    etNumber.setText(document.getString("numero"))
                    etLocation.setText(document.getString("ubicacion"))
                    etExperience.setText(document.getString("experiencia"))
                    etOtherExperience.setText(document.getString("otrasExperiencias"))

                    val timestamp = document.getTimestamp("dateCreated")
                    timestamp?.let {
                        val dateCreated = it.toDate().time
                        println("Fecha de creación: $dateCreated")
                    }

                    saveButton.visibility = View.GONE
                    updateButton.visibility = View.VISIBLE
                } else {
                    saveButton.visibility = View.VISIBLE
                    updateButton.visibility = View.GONE
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error al cargar el perfil: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun saveProfile() {
        val name = etName.text.toString()
        val years = etYears.text.toString()
        val email = etEmail.text.toString()
        val number = etNumber.text.toString()
        val location = etLocation.text.toString()
        val experience = etExperience.text.toString()
        val otherExperience = etOtherExperience.text.toString()
        val dateCreated = Timestamp.now()

        val profile = hashMapOf(
            "nombre" to name,
            "edad" to years,
            "correo" to email,
            "numero" to number,
            "ubicacion" to location,
            "experiencia" to experience,
            "otrasExperiencias" to otherExperience,
            "dateCreated" to dateCreated
        )

        val db = FirebaseFirestore.getInstance()
        db.collection("profiles").document(email).set(profile)
            .addOnSuccessListener {
                Toast.makeText(context, "Perfil guardado correctamente.", Toast.LENGTH_SHORT).show()
                saveButton.visibility = View.GONE
                updateButton.visibility = View.VISIBLE

                // Log para verificar el campo `dateCreated` después de guardar
                db.collection("profiles").document(email).get()
                    .addOnSuccessListener { document ->
                        val timestamp = document.getTimestamp("dateCreated")
                        if (timestamp != null) {
                            val time = timestamp.toDate()
                            println("Fecha de creación en Firestore: $time")
                        } else {
                            println("El campo dateCreated no se guardó correctamente.")
                        }
                    }
                    .addOnFailureListener { e ->
                        println("Error al verificar dateCreated: ${e.message}")
                    }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error al guardar el perfil: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }



    private fun updateProfile() {
        val name = etName.text.toString()
        val years = etYears.text.toString()
        val email = etEmail.text.toString()
        val number = etNumber.text.toString()
        val location = etLocation.text.toString()
        val experience = etExperience.text.toString()
        val otherExperience = etOtherExperience.text.toString()

        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("profiles").document(email)

        userRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val dateCreated = document.getTimestamp("dateCreated") ?: Timestamp.now()

                    val profile = hashMapOf(
                        "nombre" to name,
                        "edad" to years,
                        "correo" to email,
                        "numero" to number,
                        "ubicacion" to location,
                        "experiencia" to experience,
                        "otrasExperiencias" to otherExperience,
                        "dateCreated" to dateCreated
                    )

                    userRef.set(profile)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Perfil actualizado correctamente.", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Error al actualizar el perfil: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error al cargar el perfil: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
