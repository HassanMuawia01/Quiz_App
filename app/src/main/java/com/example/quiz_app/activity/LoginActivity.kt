package com.example.quiz_app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.quiz_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()
        var loginBtn=findViewById<Button>(R.id.btnLogin)
        var singupText=findViewById<TextView>(R.id.textSignup)
        loginBtn.setOnClickListener {
            loginUser()
        }

        singupText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun loginUser(){
        val Email=findViewById<EditText>(R.id.etEmail)
        val Password=findViewById<EditText>(R.id.etPassword)

        val email = Email.text.toString()
        val password=Password.text.toString()


        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user!!.isEmailVerified) {
                        Toast.makeText(this, "Login SuccessFul", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                       user.sendEmailVerification()
                        Toast.makeText(this,"Please Verify Your Mail",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    val exception = task.exception
                    when (exception) {
                        is FirebaseAuthInvalidUserException -> {
                            // Email not registered
                            Email.setError("Please Enter Valid Email")
                            Email.requestFocus()
                               }
                        is FirebaseAuthInvalidCredentialsException -> {
                            // Invalid password
                            Password.setError("Enter Valid Password")
                            Password.requestFocus()
                                       }
                        else -> {
                            // Other errors

                            Toast.makeText(this, "Authentication failed: ${exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                       }
            }
        }else{
            if (email.isEmpty() && email.isBlank()){
                Email.setError("Please Enter Email")
                Email.requestFocus()
            }

            if (password.isEmpty() && password.isBlank()){
                Password.setError("Enter Password")
                Password.requestFocus()
            }
        }
    }
}