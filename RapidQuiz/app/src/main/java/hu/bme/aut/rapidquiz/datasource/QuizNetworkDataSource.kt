package hu.bme.aut.rapidquiz.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.rapidquiz.BuildConfig
import hu.bme.aut.rapidquiz.model.QuizResult
import hu.bme.aut.rapidquiz.network.RetrofitClient
import hu.bme.aut.rapidquiz.ui.quiz.QuizViewState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object QuizNetworkDataSource {

    fun getQuizQuestions(category: String, amount: String): MutableLiveData<QuizViewState> {
        val call = RetrofitClient.apiInterface.getQuizQuestions(
            amount,category,"easy", "multiple", "url3986")

        val quizResultData = MutableLiveData<QuizViewState>()
        quizResultData.value = QuizViewState.InProgress

        call.enqueue(object: Callback<QuizResult> {
            override fun onResponse(call: Call<QuizResult>, response: Response<QuizResult>) {
                Log.d("DEBUG : ", response.body().toString())
                quizResultData.value = QuizViewState.QuizQueryResponseSuccess(response.body()!!)
            }

            override fun onFailure(call: Call<QuizResult>, t: Throwable) {
                Log.d("DEBUG : ", t.message.toString())
                quizResultData.value = QuizViewState.QuizQueryResponseError(t.message!!)
            }

        })

        return quizResultData
    }

}