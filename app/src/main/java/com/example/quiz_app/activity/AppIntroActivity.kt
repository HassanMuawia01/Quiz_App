package com.example.quiz_app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.quiz_app.R
import com.google.firebase.auth.FirebaseAuth

class AppIntroActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_intro)
        val getStartBtn=findViewById<Button>(R.id.btnGetStarted)

        firebaseAuth= FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null){
            Toast.makeText(this, "Already Login", Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }

        getStartBtn.setOnClickListener {
            redirect("LOGIN")
        }
    }
    private fun redirect(name:String){
        val intent = when(name){
            "LOGIN" -> Intent(this, LoginActivity::class.java)
            "MAIN"  -> Intent(this, MainActivity::class.java)
            else    -> throw Exception ("No Path exists")
        }
        startActivity(intent)
        finish()
    }
}