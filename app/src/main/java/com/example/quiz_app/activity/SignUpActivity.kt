package com.example.quiz_app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.quiz_app.R
import com.example.quiz_app.models.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User

class SignUpActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
       firebaseAuth=FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("users")
        val signUPBtn=findViewById<Button>(R.id.btnSignUP)
        val loginText=findViewById<TextView>(R.id.textlogin)
        signUPBtn.setOnClickListener {
            SignupUser()
        }

loginText.setOnClickListener {
    val intent = Intent(this, LoginActivity::class.java)
    startActivity(intent)
    finish()
}

    }
   private fun SignupUser(){
       val etEmail=findViewById<EditText>(R.id.etEmail)
       val etPassword=findViewById<EditText>(R.id.etPassword)
       val etConfirmPassword=findViewById<EditText>(R.id.etConfirmPassword)

        val Email:String =etEmail.text.toString()
       val Password = etPassword.text.toString()
       val confirmPassword = etConfirmPassword.text.toString()


       if (Password.length <8){
           etPassword.setError("must b 8 number entered")
           etPassword.requestFocus()
       }

       if (!Password.equals(confirmPassword)){
           Toast.makeText(this, "Password do not Match", Toast.LENGTH_SHORT).show()
       }
       if (!EMAIL_ADDRESS.matcher(Email).matches()){
           etEmail.setError("Enter Valid Email")
           etEmail.requestFocus()
       }

       if (Email.isNotEmpty() && Password.isNotEmpty() && confirmPassword.isNotEmpty()) {
           firebaseAuth.createUserWithEmailAndPassword(Email, Password)
               .addOnCompleteListener(this) {
                   if (it.isSuccessful) {
                       val user = firebaseAuth.currentUser
                       val userId = user?.uid ?: ""
                       val newUser = user(Email, Password)
                       db.child(userId).setValue(newUser).addOnSuccessListener {
                               Toast.makeText(this, "User Create", Toast.LENGTH_SHORT).show()

                           }
                       val intent = Intent(this, MainActivity::class.java)
                       startActivity(intent)
                       finish()
                   } else {
                       Toast.makeText(this, "Already Email Use", Toast.LENGTH_SHORT).show()
                   }
               }
       }else{
           if (Email.isEmpty()){
               etEmail.setError("Enter Email")
               etEmail.requestFocus()
           }
           if (Password.isEmpty()){
               etPassword.setError("Enter Password")
               etPassword.requestFocus()
           }
           if (confirmPassword.isEmpty()){
               etConfirmPassword.setError("Enter a confirm password")
               etConfirmPassword.requestFocus()
           }
       }
    }
}