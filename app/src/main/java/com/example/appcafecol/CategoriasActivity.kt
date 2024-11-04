package com.example.appcafecol

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView

class CategoriasActivity : AppCompatActivity() {

    lateinit var navegation: BottomNavigationView

    private val monNavmenu = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.itemFragment1 -> {
                supportFragmentManager.commit {
                    replace<PrimerFragment>(R.id.framecontainer)
                    setReorderingAllowed(true)
                    addToBackStack(null) // Se usa null para no nombrar la pila
                }
                true
            }
            R.id.itemFragment2 -> {
                supportFragmentManager.commit {
                    replace<SegundoFragment>(R.id.framecontainer)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
                true
            }
            R.id.itemFragment3 -> {
                supportFragmentManager.commit {
                    replace<TercerFragment>(R.id.framecontainer)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
                true
            }
            else -> false
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_categorias)


        navegation = findViewById(R.id.navMenu)
        navegation.setOnNavigationItemSelectedListener(monNavmenu) // Cambiado a OnNavigationItemSelectedListener



        supportFragmentManager.commit {
            replace<PrimerFragment>(R.id.framecontainer)
            setReorderingAllowed(true)
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}