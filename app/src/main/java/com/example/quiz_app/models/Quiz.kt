package com.example.quiz_app.models

data class Quiz (
    var id: String = " ",
    var tittle: String = " ",
    var question: MutableMap<String, Question> = mutableMapOf()
)