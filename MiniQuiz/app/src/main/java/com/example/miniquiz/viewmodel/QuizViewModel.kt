package com.example.miniquiz.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.miniquiz.model.Question
import com.example.miniquiz.model.QuestionBank
import com.example.miniquiz.model.WrongAnswer

enum class Screen { START, QUIZ, RESULT }

class QuizViewModel : ViewModel() {

    val maxQuestions: Int = QuestionBank.questions.size

    var screen by mutableStateOf(Screen.START)
        private set

    var countInput by mutableStateOf("3")
        private set
    var countError by mutableStateOf<String?>(null)
        private set

    private var quizQuestions: List<Question> = emptyList()
    private val userAnswers = mutableListOf<Int>()

    var currentIndex by mutableStateOf(0)
        private set
    var selectedOption by mutableStateOf<Int?>(null)
        private set

    val currentQuestion: Question? get() = quizQuestions.getOrNull(currentIndex)

    var score by mutableStateOf(0)
        private set
    var totalQuestions by mutableStateOf(0)
        private set
    var wrongAnswers by mutableStateOf<List<WrongAnswer>>(emptyList())
        private set

    private fun validateCount(text: String): String? {
        if (text.isEmpty()) return null
        val n = text.toIntOrNull() ?: return "Invalid number"
        return when {
            n < 1 -> "Minimum is 1 question"
            n > maxQuestions -> "Maximum is $maxQuestions questions"
            else -> null
        }
    }

    val canStart: Boolean get() = countInput.isNotEmpty() && countError == null

    fun onCountChange(value: String) {
        countInput = value.filter { it.isDigit() }.take(3)
        countError = validateCount(countInput)
    }

    fun startQuiz() {
        val error = validateCount(countInput)
        if (error != null || countInput.isEmpty()) {
            countError = error
            return
        }
        quizQuestions = QuestionBank.questions.shuffled().take(countInput.toInt())
        userAnswers.clear()
        currentIndex = 0
        selectedOption = null
        screen = Screen.QUIZ
    }

    fun selectOption(index: Int) {
        selectedOption = index
    }

    fun next() {
        val chosen = selectedOption ?: return
        userAnswers.add(chosen)
        if (currentIndex < quizQuestions.lastIndex) {
            currentIndex++
            selectedOption = null
        } else {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
        val wrong = mutableListOf<WrongAnswer>()
        var correct = 0
        quizQuestions.forEachIndexed { i, question ->
            val picked = userAnswers[i]
            if (picked == question.correctIndex) {
                correct++
            } else {
                wrong.add(
                    WrongAnswer(
                        number = i + 1,
                        question = question.text,
                        yourAnswer = question.options[picked],
                        correctAnswer = question.options[question.correctIndex]
                    )
                )
            }
        }
        score = correct
        totalQuestions = quizQuestions.size
        wrongAnswers = wrong
        screen = Screen.RESULT
    }

    fun restart() {
        countError = null
        countInput = totalQuestions.toString()
        screen = Screen.START
    }
}
