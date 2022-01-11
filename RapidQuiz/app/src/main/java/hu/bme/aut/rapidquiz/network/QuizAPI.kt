package hu.bme.aut.rapidquiz.network

import hu.bme.aut.rapidquiz.model.QuizResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//https://opentdb.com/api.php?amount=10&category=18&difficulty=easy&type=multiple&encode=url3986

interface QuizAPI {
    @GET("/api.php")
    fun getQuizQuestions(@Query("amount") amount: String,
                               @Query("category") category: String,
                               @Query("difficulty") difficulty: String,
                               @Query("type") type: String,
                               @Query("encode") encode: String): Call<QuizResult>
}