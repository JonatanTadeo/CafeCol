package com.example.appcafecol

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView

class CategoriasActivity : AppCompatActivity() {

    lateinit var navegation: BottomNavigationView

    private val monNavmenu = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.itemFragment1 -> {
                supportFragmentManager.commit {
                    replace(R.id.framecontainer, PrimerFragment())
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
                true
            }
            R.id.itemFragment2 -> {
                supportFragmentManager.commit {
                    replace(R.id.framecontainer, SegundoFragment())
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
                true
            }
            R.id.itemFragment3 -> {
                val email = intent.getStringExtra("email")
                supportFragmentManager.commit {
                    replace(R.id.framecontainer, TercerFragment.newInstance(email!!))
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
                true
            }
            R.id.itemFragment4 -> {
                val email = intent.getStringExtra("email")
                supportFragmentManager.commit {
                    replace(R.id.framecontainer, CuartoFragment.newInstance(email!!))
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
                true
            }
            R.id.itemFragment5 -> {
                supportFragmentManager.commit {
                    replace(R.id.framecontainer, QuintoFragment())
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
        setContentView(R.layout.activity_categorias)

        val email = intent.getStringExtra("email")

        navegation = findViewById(R.id.navMenu)
        navegation.setOnNavigationItemSelectedListener(monNavmenu)

        supportFragmentManager.commit {
            replace(R.id.framecontainer, PrimerFragment())
            setReorderingAllowed(true)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
