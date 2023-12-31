package com.example.fatafatmangwao.repository

import com.example.fatafatmangwao.OtpData
import com.example.fatafatmangwao.networkmodule.AuthApiService
import com.example.fatafatmangwao.model.category.CategoryModel
import com.example.fatafatmangwao.model.RegistrationResponse
import com.example.fatafatmangwao.model.ResendOtpResponse
import com.example.fatafatmangwao.model.User
import com.example.fatafatmangwao.model.VerifyOtpResponse
import com.example.fatafatmangwao.model.shops.ShopsModel

class ApiRepository {

    suspend fun registerUser(registrationDetails: User): RegistrationResponse {
        return AuthApiService.authApiInterface.registerUser(registrationDetails)
    }

    suspend fun verifyOtp(otp: OtpData): VerifyOtpResponse {
        return AuthApiService.authApiInterface.verifyOtp(otp)
    }

    suspend fun resendOtp(): ResendOtpResponse {
        return AuthApiService.authApiInterface.resendOtp()
    }

    suspend fun loginUser(loginDetails: User): RegistrationResponse {
        return AuthApiService.authApiInterface.loginUser(loginDetails)
    }

    suspend fun getCategories(limit: Int): CategoryModel {
        return AuthApiService.authApiInterface.getCategories(limit)
    }

    suspend fun getAllShops(shopName: String, categoryId: String): ShopsModel {
        return AuthApiService.authApiInterface.getAllShops(shopName, categoryId)
    }
}