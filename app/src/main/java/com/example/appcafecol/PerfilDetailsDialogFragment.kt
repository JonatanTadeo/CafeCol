package com.example.appcafecol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class PerfilDetailsDialogFragment : DialogFragment() {

    private lateinit var perfil: Perfil

    companion object {
        private const val ARG_PERFIL = "perfil"

        fun newInstance(perfil: Perfil): PerfilDetailsDialogFragment {
            val fragment = PerfilDetailsDialogFragment()
            val args = Bundle()
            args.putParcelable(ARG_PERFIL, perfil)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            perfil = it.getParcelable(ARG_PERFIL)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil_details_dialog, container, false)

        view.findViewById<TextView>(R.id.nombreTextView).text = perfil.nombre
        view.findViewById<TextView>(R.id.edadTextView).text = perfil.edad
        view.findViewById<TextView>(R.id.correoTextView).text = perfil.correo
        view.findViewById<TextView>(R.id.numeroTextView).text = perfil.numero
        view.findViewById<TextView>(R.id.ubicacionTextView).text = perfil.ubicacion
        view.findViewById<TextView>(R.id.experienciaTextView).text = perfil.experiencia
        view.findViewById<TextView>(R.id.otrasExperienciasTextView).text = perfil.otrasExperiencias

        return view
    }
}

