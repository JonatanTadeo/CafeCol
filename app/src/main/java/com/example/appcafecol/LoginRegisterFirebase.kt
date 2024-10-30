package com.example.appcafecol

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class LoginRegisterFirebase : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_register_firebase)


    }
    fun register(view: View){
        loginregister()
    }
    private fun loginregister(){
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }
    fun login(view: View){
        loginlogin()
    }
    private fun loginlogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


}