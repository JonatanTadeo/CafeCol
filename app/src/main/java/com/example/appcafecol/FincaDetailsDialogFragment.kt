package com.example.appcafecol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class FincaDetailsDialogFragment : DialogFragment() {

    private lateinit var finca: Finca

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

        return view
    }
}



