package com.example.quiz_app.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.quiz_app.R
import com.example.quiz_app.models.Quiz
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson


class ResultActivity : AppCompatActivity() {
    lateinit var quiz: Quiz
    private lateinit var textScore : TextView
    private lateinit var textAnswer : TextView
    lateinit var navigationView: NavigationView
    lateinit var topAppBar: MaterialToolbar
   private lateinit var mainDrawer: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        textScore = findViewById(R.id.txtScore)
        textAnswer = findViewById(R.id.txtAnswer)
        navigationView = findViewById(R.id.navigationView)
        topAppBar = findViewById(R.id.topAppBar)
        mainDrawer = findViewById(R.id.mainDrawer)
        setUpView()
    }

    private fun setUpView() {
        //decelerice method for gson to get string
        val quizData = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson<Quiz>(quizData, Quiz::class.java)
        calculateScore()
        setAnswerView()
        setUpDrawerLayout()
    }

    private fun setAnswerView() {
        val builder = StringBuilder("")
        for (entry in quiz.question.entries) {
            val question = entry.value
            builder.append("<font color'#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${question.answer}</font><br/><br/>")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            textAnswer.text = Html.fromHtml(builder.toString());
        }
        }


    private fun calculateScore() {
        var score = 0
        for(entry in quiz.question.entries){
            val question = entry.value
            if (question.answer == question.useranswer){
                score += 10
            }
        }
        textScore.text = "Your Score is: $score"

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
                    finish()
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