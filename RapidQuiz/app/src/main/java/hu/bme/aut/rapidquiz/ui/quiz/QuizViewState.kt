package hu.bme.aut.rapidquiz.ui.quiz

import hu.bme.aut.rapidquiz.model.QuizResult


sealed class QuizViewState {
    object InProgress : QuizViewState()
    data class QuizQueryResponseSuccess( val data: QuizResult): QuizViewState()
    data class QuizQueryResponseError( val exceptionMsg: String): QuizViewState()
}