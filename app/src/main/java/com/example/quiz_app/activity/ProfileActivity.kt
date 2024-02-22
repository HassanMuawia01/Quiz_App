package com.example.quiz_app.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.quiz_app.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var textEmail :TextView
    private lateinit var btnLogout : Button

    lateinit var navigationView: NavigationView
    lateinit var topAppBar: MaterialToolbar
    private lateinit var mainDrawer: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        navigationView = findViewById(R.id.navigationView)
        topAppBar = findViewById(R.id.topAppBar)
        mainDrawer = findViewById(R.id.mainDrawer)
        firebaseAuth = FirebaseAuth.getInstance()

        textEmail = findViewById(R.id.txtEmail)
        textEmail.text = firebaseAuth.currentUser?.email
        setUpDrawerLayout()
        btnLogout = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun setUpDrawerLayout(){
        setSupportActionBar(topAppBar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, mainDrawer, R.string.app_name, R.string.app_name)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnhome -> {
                    // Open HomeActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                R.id.btnProfile -> {
                    // Open ProfileActivity
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)

                }

            }
            mainDrawer.closeDrawers()
            true

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}