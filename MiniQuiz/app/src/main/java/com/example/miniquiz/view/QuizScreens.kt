package com.example.miniquiz.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.miniquiz.viewmodel.QuizViewModel
import com.example.miniquiz.viewmodel.Screen

private val Navy = Color(0xFF4A5B8C)
private val Light = Color(0xFFDDE1F5)

@Composable
fun QuizApp(vm: QuizViewModel = viewModel()) {
    when (vm.screen) {
        Screen.START -> StartScreen(vm)
        Screen.QUIZ -> QuestionScreen(vm)
        Screen.RESULT -> ResultScreen(vm)
    }
}

@Composable
fun StartScreen(vm: QuizViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mini Quiz", fontSize = 22.sp)
        Text("How many questions do you want? (1 - ${vm.maxQuestions})", fontSize = 12.sp)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = vm.countInput,
            onValueChange = vm::onCountChange,
            label = { Text("Number of questions") },
            singleLine = true,
            isError = vm.countError != null,
            supportingText = { vm.countError?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = vm::startQuiz,
            enabled = vm.canStart,
            colors = ButtonDefaults.buttonColors(containerColor = Navy),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Start Quiz") }
    }
}

@Composable
fun QuestionScreen(vm: QuizViewModel) {
    val question = vm.currentQuestion ?: return
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Question ${vm.currentIndex + 1}", fontSize = 22.sp)
        Text(question.text, fontSize = 13.sp, textAlign = TextAlign.Center)
        Spacer(Modifier.height(16.dp))
        question.options.forEachIndexed { index, option ->
            val selected = vm.selectedOption == index
            Button(
                onClick = { vm.selectOption(index) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected) Navy else Light,
                    contentColor = if (selected) Color.White else Color.Black
                ),
                modifier = Modifier.fillMaxWidth().height(40.dp)
            ) { Text(option, fontSize = 12.sp) }
            Spacer(Modifier.height(8.dp))
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = vm::next,
            enabled = vm.selectedOption != null,
            colors = ButtonDefaults.buttonColors(containerColor = Navy),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Next") }
    }
}

@Composable
fun ResultScreen(vm: QuizViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Quiz Finished!", fontSize = 22.sp)
                Text("Your Score: ${vm.score} / ${vm.totalQuestions}", fontSize = 13.sp)
                Spacer(Modifier.height(16.dp))
            }
        }
        if (vm.wrongAnswers.isEmpty()) {
            item { Text("Perfect! No wrong answers.", color = Navy, fontSize = 13.sp) }
        } else {
            items(vm.wrongAnswers) { wrong ->
                Column(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Text("Q${wrong.number}: ${wrong.question}", fontSize = 12.sp)
                    Text("Your Answer: ${wrong.yourAnswer}", fontSize = 11.sp, color = Color.Red)
                    Text("Correct Answer: ${wrong.correctAnswer}", fontSize = 11.sp, color = Navy)
                    HorizontalDivider(Modifier.padding(top = 6.dp))
                }
            }
        }
        item {
            Column {
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = vm::restart,
                    colors = ButtonDefaults.buttonColors(containerColor = Navy),
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Restart Quiz") }
            }
        }
    }
}

@Preview(showBackground = true, name = "Start")
@Composable
fun StartScreenPreview() {
    MaterialTheme { StartScreen(QuizViewModel()) }
}

@Preview(showBackground = true, name = "Start - error")
@Composable
fun StartScreenErrorPreview() {
    val vm = QuizViewModel().apply { onCountChange("30") }
    MaterialTheme { StartScreen(vm) }
}

@Preview(showBackground = true, name = "Question")
@Composable
fun QuestionScreenPreview() {
    val vm = QuizViewModel().apply {
        onCountChange("3"); startQuiz(); selectOption(2)
    }
    MaterialTheme { QuestionScreen(vm) }
}

@Preview(showBackground = true, name = "Result")
@Composable
fun ResultScreenPreview() {
    val vm = QuizViewModel().apply {
        onCountChange("3"); startQuiz()
        repeat(3) { selectOption(0); next() }
    }
    MaterialTheme { ResultScreen(vm) }
}
