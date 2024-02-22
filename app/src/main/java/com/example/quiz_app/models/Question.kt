package com.example.quiz_app.models

data class Question (
    var description: String = " ",
    var option1: String = " ",
    var option2: String = " ",
    var option3: String = " ",
    var option4: String = " ",
    var answer: String = " ",
    var useranswer: String = " "
    //useranswer ka matlb ye ho ga k user ne kia select kia h
)