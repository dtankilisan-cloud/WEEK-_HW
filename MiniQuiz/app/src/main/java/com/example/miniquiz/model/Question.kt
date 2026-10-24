package com.example.miniquiz.model

data class Question(
    val text: String,
    val options: List<String>,
    val correctIndex: Int
)

data class WrongAnswer(
    val number: Int,
    val question: String,
    val yourAnswer: String,
    val correctAnswer: String
)
