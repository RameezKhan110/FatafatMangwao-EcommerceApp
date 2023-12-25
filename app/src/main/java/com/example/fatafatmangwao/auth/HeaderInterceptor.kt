package com.example.fatafatmangwao.auth

import com.example.fatafatmangwao.ApplicationClass
import com.example.fatafatmangwao.Extensions
import com.example.fatafatmangwao.SharedViewModel
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val modifiedRequest = originalRequest.newBuilder()
            .header(
                "Authorization",
                "Bearer ${Extensions.getUserToken(ApplicationClass.getContext())}"
            )
            .build()


        val responseModified = chain.proceed(modifiedRequest)

        return if (responseModified.isSuccessful) {
            responseModified
        } else {
            Response.Builder()
                .request(responseModified.request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("ERROR")
                .body(responseModified.peekBody(10000).string().createErrorMessage())
                .build()
        }
    }

}


fun String.createErrorMessage(): ResponseBody {
    val mediaType = "application/json".toMediaTypeOrNull()
    return ResponseBody.create(mediaType, this)
}