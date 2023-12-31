package com.example.fatafatmangwao.networkmodule

import com.example.fatafatmangwao.utils.Extensions
import com.example.fatafatmangwao.OtpData
import com.example.fatafatmangwao.model.category.CategoryModel
import com.example.fatafatmangwao.model.RegistrationResponse
import com.example.fatafatmangwao.model.ResendOtpResponse
import com.example.fatafatmangwao.model.User
import com.example.fatafatmangwao.model.VerifyOtpResponse
import com.example.fatafatmangwao.model.shops.ShopsModel
import com.example.fatafatmangwao.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiInterface {

    @POST("user/register")
    suspend fun registerUser(@Body registrationDetails: User): RegistrationResponse

    @POST("user/verify")
    suspend fun verifyOtp(@Body otp: OtpData): VerifyOtpResponse

    @GET("user/otp")
    suspend fun resendOtp(): ResendOtpResponse

    @POST("user/login")
    suspend fun loginUser(@Body loginDetails: User): RegistrationResponse

    @GET("category?limit=2")
    suspend fun getCategories(@Query("limit") limit: Int): CategoryModel

    @GET("shop/get?")
    suspend fun getAllShops(@Query("search") shopName: String, @Query("category") categoryId: String): ShopsModel

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
