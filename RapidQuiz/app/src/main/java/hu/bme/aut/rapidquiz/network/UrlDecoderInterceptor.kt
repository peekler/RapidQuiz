package hu.bme.aut.rapidquiz.network

import okhttp3.ResponseBody

import android.text.TextUtils

import androidx.annotation.NonNull

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.net.URLDecoder

class UrlDecoderInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())
        if (response.isSuccessful()) {
            val newResponse: Response.Builder = response.newBuilder()
            var decryptedString: String = URLDecoder.decode(response.body().toString(),"UTF-8")

            newResponse.body(ResponseBody.create(MediaType.parse(response.header("Content-Type")), decryptedString))
            return newResponse.build()
        }
        return response
    }

}