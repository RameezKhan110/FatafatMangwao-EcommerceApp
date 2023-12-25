package com.example.fatafatmangwao.auth

import com.example.fatafatmangwao.Extensions
import com.example.fatafatmangwao.OtpData
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiInterface {

    @POST("user/register")
    suspend fun registerUser(@Body registrationDetails: User): RegistrationResponse

    @POST("user/verify")
    suspend fun verifyOtp(@Body otp: OtpData): VerifyOtpResponse

    @GET("user/otp")
    suspend fun resendOtp(): ResendOtpResponse

    @POST("user/login")
    suspend fun loginUser(@Body loginDetails: User): RegistrationResponse
}

object AuthApiService {

    val authApiInterface: AuthApiInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(Extensions.createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        authApiInterface = retrofit.create()
    }
}

//object AuthApiServiceWithToken {
//
//    val authApiInterface: AuthApiInterface
//
//    init {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(Extensions.createOkHttpClient())
//            .build()
//        authApiInterface = retrofit.create()
//    }
//}