package hu.bme.aut.rapidquiz.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.rapidquiz.repository.QuizRepository


class QuizViewModel : ViewModel() {
    private var quizRepository: QuizRepository = QuizRepository()

    fun getQuizQuestions(category: String, amount: String) : LiveData<QuizViewState>? {
        return quizRepository.getQuizQuestions(category, amount)
    }
}