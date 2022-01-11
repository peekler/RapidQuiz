package hu.bme.aut.rapidquiz.network

import hu.bme.aut.rapidquiz.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    private val retrofitClient: Retrofit.Builder by lazy {

/*        val okHttpClient = OkHttpClient()
            .newBuilder() //httpLogging interceptor for logging network requests
            .addInterceptor(UrlDecoderInterceptor())
            .build()*/

        Retrofit.Builder()
            .baseUrl(BuildConfig.QUIZ_BASE_URL)
            //.client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
    }

    val apiInterface: QuizAPI by lazy {
        retrofitClient
            .build()
            .create(QuizAPI::class.java)
    }
}