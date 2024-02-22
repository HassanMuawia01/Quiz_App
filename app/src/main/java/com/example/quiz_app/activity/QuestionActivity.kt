package com.example.quiz_app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz_app.R
import com.example.quiz_app.adapters.OptionAdapter
import com.example.quiz_app.models.Question
import com.example.quiz_app.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.google.gson.Gson

class QuestionActivity : AppCompatActivity() {
    private lateinit var description:TextView
    private lateinit var option_List:RecyclerView
    var quiz : MutableList<Quiz> ?= null
    var question : MutableMap<String,Question> ?= null
    var index = 1

    private lateinit var btnPervious:TextView
    private lateinit var btnNext:TextView
    private lateinit var btnSubmit:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        description = findViewById(R.id.description)
        option_List = findViewById(R.id.option_List)
        btnPervious = findViewById(R.id.btnPrevious)
        btnNext = findViewById(R.id.btnNext)
        btnSubmit = findViewById(R.id.btnSubmit)
        setUpFireStore()
        setUpEventListener()
    }

    private fun setUpEventListener() {
        btnPervious.setOnClickListener {
            index--
            bindViews()
        }
        btnNext.setOnClickListener {
            index++
            bindViews()
        }
        btnSubmit.setOnClickListener {
            Log.d("Submit", question.toString())

            val intent = Intent(this, ResultActivity::class.java)
            val json  = Gson().toJson(quiz!![0])
            intent.putExtra("QUIZ", json)
            startActivity(intent)
            finish()
        }
    }

    private fun setUpFireStore() {
        val firestore = FirebaseFirestore.getInstance()
        val date=intent.getStringExtra("DATE")
        if (date != null){
         firestore.collection("quizess").whereEqualTo("tittle",date).get()
             .addOnSuccessListener {
                 if (it != null && !it.isEmpty) {
                     quiz = it.toObjects(Quiz::class.java)
                     question = quiz!![0].question
                     bindViews()

                 }else{
                     Toast.makeText(this, "data is not exist", Toast.LENGTH_SHORT).show()
                 }
             }
             }
    }

    private fun bindViews() {
        btnPervious.visibility = View.GONE
        btnNext.visibility = View.GONE
        btnSubmit.visibility = View.GONE

        if (index == 1){//first question
            btnNext.visibility =View.VISIBLE

        }
        else if (index == question!!.size){ //last question
            btnSubmit.visibility = View.VISIBLE

        }
        else{//Middle
            btnPervious.visibility = View.VISIBLE
            btnNext.visibility = View.VISIBLE

        }
        val question = question!!["question$index"]
        question?.let {
            description.text=question.description
            val optionalAdapter=OptionAdapter(this,question)
            option_List.layoutManager = LinearLayoutManager(this)
            option_List.adapter = optionalAdapter
            //option_List.setHasFixedSize(true)

        }


    }
}