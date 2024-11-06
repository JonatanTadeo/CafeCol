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

class CuartoFragment : Fragment() {

    private lateinit var etFinca: EditText
    private lateinit var etCharge: EditText
    private lateinit var etAvailability: EditText
    private lateinit var etLocation: EditText
    private lateinit var etSalary: EditText
    private lateinit var etWork: EditText
    private lateinit var etExperience: EditText
    private lateinit var saveButton: Button
    private lateinit var updateButton: Button

    private var fincaName: String? = null
    private val db = FirebaseFirestore.getInstance() // Instancia de Firestore

    companion object {
        @JvmStatic
        fun newInstance(fincaName: String) =
            CuartoFragment().apply {
                arguments = Bundle().apply {
                    putString("fincaName", fincaName)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fincaName = it.getString("fincaName")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cuarto, container, false)

        etFinca = view.findViewById(R.id.finca_edit_text)
        etCharge = view.findViewById(R.id.charge_edit_text)
        etAvailability = view.findViewById(R.id.availability_edit_text)
        etLocation = view.findViewById(R.id.location_edit_text)
        etSalary = view.findViewById(R.id.salary_edit_text)
        etWork = view.findViewById(R.id.work_edit_text)
        etExperience = view.findViewById(R.id.experience_edit_text)
        saveButton = view.findViewById(R.id.save_button)
        updateButton = view.findViewById(R.id.update_button)

        saveButton.setOnClickListener {
            saveFinca()
        }

        updateButton.setOnClickListener {
            updateFinca()
        }

        fincaName?.let { loadFinca(it) }

        return view
    }

    private fun loadFinca(fincaName: String) {
        val fincaRef = db.collection("farms").document(fincaName)

        fincaRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    etFinca.setText(document.getString("finca"))
                    etCharge.setText(document.getString("charge"))
                    etAvailability.setText(document.getString("availability"))
                    etLocation.setText(document.getString("location"))
                    etSalary.setText(document.getString("salary"))
                    etWork.setText(document.getString("work"))
                    etExperience.setText(document.getString("experience"))

                    // Verificación del campo `dateCreated`
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
                Toast.makeText(context, "Error al cargar la finca: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun saveFinca() {
        val finca = etFinca.text.toString()
        val charge = etCharge.text.toString()
        val availability = etAvailability.text.toString()
        val location = etLocation.text.toString()
        val salary = etSalary.text.toString()
        val work = etWork.text.toString()
        val experience = etExperience.text.toString()
        val dateCreated = Timestamp.now()

        val fincaData = hashMapOf(
            "finca" to finca,
            "charge" to charge,
            "availability" to availability,
            "location" to location,
            "salary" to salary,
            "work" to work,
            "experience" to experience,
            "dateCreated" to dateCreated
        )

        val db = FirebaseFirestore.getInstance()
        db.collection("farms").document(finca).set(fincaData)
            .addOnSuccessListener {
                Toast.makeText(context, "Finca guardada correctamente.", Toast.LENGTH_SHORT).show()
                saveButton.visibility = View.GONE
                updateButton.visibility = View.VISIBLE

                // Verificar el campo `dateCreated` después de guardar
                db.collection("farms").document(finca).get()
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
                Toast.makeText(context, "Error al guardar la finca: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun updateFinca() {
        val finca = etFinca.text.toString()
        val charge = etCharge.text.toString()
        val availability = etAvailability.text.toString()
        val location = etLocation.text.toString()
        val salary = etSalary.text.toString()
        val work = etWork.text.toString()
        val experience = etExperience.text.toString()

        fincaName?.let {
            val fincaRef = db.collection("farms").document(it)

            fincaRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Si la finca ya existe, mantenemos la fecha de creación
                        val dateCreated = document.getTimestamp("dateCreated") ?: Timestamp.now()

                        val fincaData = hashMapOf(
                            "finca" to finca,
                            "charge" to charge,
                            "availability" to availability,
                            "location" to location,
                            "salary" to salary,
                            "work" to work,
                            "experience" to experience,
                            "dateCreated" to dateCreated
                        )

                        fincaRef.set(fincaData)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Finca actualizada correctamente.", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Error al actualizar la finca: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Error al cargar la finca: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

}








