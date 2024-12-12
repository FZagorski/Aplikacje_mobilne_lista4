package com.example.zad4

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class QuizActivity : ComponentActivity() {
    private val quizData = Answers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizScreen(
                questions = quizData.questions,
                answers = quizData.answersArray,
                correctAnswers = quizData.correctAnswers,
                onFinishQuiz = { score ->
                    val intent = Intent(this, ScoreActivity::class.java)
                    intent.putExtra("score", score)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}

@Composable
fun QuizScreen(
    questions: Array<String>,
    answers: Array<Array<String>>,
    correctAnswers: Array<String>,
    onFinishQuiz: (score: Int) -> Unit
) {
    var questionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Pytanie ${questionIndex + 1}/${questions.size}",
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Start)
        )
        LinearProgressIndicator(
            progress = (questionIndex + 1).toFloat() / questions.size,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = questions[questionIndex],
            fontSize = 25.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        answers[questionIndex].forEach { answer ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                RadioButton(
                    selected = selectedAnswer == answer,
                    onClick = { selectedAnswer = answer }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = answer)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (selectedAnswer == correctAnswers[questionIndex]) {
                    score++
                }
                if (questionIndex < questions.size - 1) {
                    questionIndex++
                    selectedAnswer = null
                } else {
                    onFinishQuiz(score)
                }
            },
            enabled = selectedAnswer != null
        ) {
            Text(text = "Następne pytanie")
        }
    }
}