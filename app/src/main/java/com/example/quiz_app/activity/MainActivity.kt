package com.example.quiz_app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz_app.R
import com.example.quiz_app.adapters.QuizAdapter
import com.example.quiz_app.models.Quiz
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var topAppBar: MaterialToolbar
    lateinit var mainDrawer: DrawerLayout
    lateinit var adapter: QuizAdapter
    private var quizlist= mutableListOf<Quiz>()
    lateinit var quizRecyclerView: RecyclerView
    lateinit var firestore: FirebaseFirestore
    lateinit var btnDatePicker: FloatingActionButton
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        topAppBar = findViewById(R.id.topAppBar)
        mainDrawer = findViewById(R.id.mainDrawer)
        quizRecyclerView = findViewById(R.id.quizRecyclerView)
        btnDatePicker = findViewById(R.id.btndatePicker)
        navigationView = findViewById(R.id.navigationView)
        setUpViews()
    }

    private fun setUpViews(){
        setUpDrawerLayout()
        setUpRecyclerView()
        setUpFireStore()
        setUpDatePicker()
    }

    private fun setUpDatePicker() {
        btnDatePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER",datePicker.headerText)
                val dateFormat  = SimpleDateFormat ("dd-mm-yyyy")
                val date = dateFormat.format(Date(it))
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("DATE",date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER",datePicker.headerText)
            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER","Date  Picker Cancelled")
            }
        }
    }

    private fun setUpFireStore() {
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("quizess")
        collectionReference.addSnapshotListener{ value, error ->
            if (value == null || error != null){
                Toast.makeText(this, "Error Fetching data", Toast.LENGTH_SHORT).show()
            }
            Log.d("Data",value?.toObjects(Quiz::class.java).toString())
            // pely sara dummy data clear karo phr firestore wala sara data addall kar k adaptor
            // ko notify karo k data change ho gya h
            quizlist.clear()
            value?.toObjects(Quiz::class.java)?.let { quizlist.addAll(it) }
            adapter.notifyDataSetChanged()
        }

    }

    private fun setUpRecyclerView(){
        adapter = QuizAdapter(this, quizlist)
        quizRecyclerView.layoutManager = GridLayoutManager(this,2)
        quizRecyclerView.adapter = adapter
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
