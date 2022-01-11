package hu.bme.aut.rapidquiz.repository

import androidx.lifecycle.MutableLiveData
import hu.bme.aut.rapidquiz.datasource.QuizNetworkDataSource
import hu.bme.aut.rapidquiz.ui.quiz.QuizViewState


class QuizRepository {
    fun getQuizQuestions(category: String, amount: String) : MutableLiveData<QuizViewState> {
        return QuizNetworkDataSource.getQuizQuestions(category, amount)
    }
}