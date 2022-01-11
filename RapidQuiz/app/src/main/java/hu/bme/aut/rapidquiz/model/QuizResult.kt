package hu.bme.aut.rapidquiz.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuizResult(
    var response_code: Int,
    var results: List<QuizQuestion>
)

@JsonClass(generateAdapter = true)
data class QuizQuestion(
    var category: String,
    var correct_answer: String,
    var difficulty: String,
    var incorrect_answers: List<String>,
    var question: String,
    var type: String
)