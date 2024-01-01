package com.example.fatafatmangwao.repository

import com.example.fatafatmangwao.OtpData
import com.example.fatafatmangwao.networkmodule.ApiService
import com.example.fatafatmangwao.model.category.CategoryModel
import com.example.fatafatmangwao.model.RegistrationResponse
import com.example.fatafatmangwao.model.ResendOtpResponse
import com.example.fatafatmangwao.model.User
import com.example.fatafatmangwao.model.VerifyOtpResponse
import com.example.fatafatmangwao.model.shops.ShopsModel
import com.example.fatafatmangwao.model.specific_shops.SpecificShopModel

class ApiRepository {

    suspend fun registerUser(registrationDetails: User): RegistrationResponse {
        return ApiService.apiInterface.registerUser(registrationDetails)
    }

    suspend fun verifyOtp(otp: OtpData): VerifyOtpResponse {
        return ApiService.apiInterface.verifyOtp(otp)
    }

    suspend fun resendOtp(): ResendOtpResponse {
        return ApiService.apiInterface.resendOtp()
    }

    suspend fun loginUser(loginDetails: User): RegistrationResponse {
        return ApiService.apiInterface.loginUser(loginDetails)
    }

    suspend fun getCategories(limit: Int): CategoryModel {
        return ApiService.apiInterface.getCategories(limit)
    }

    suspend fun getAllShops(shopName: String, categoryId: String): ShopsModel {
        return ApiService.apiInterface.getAllShops(shopName, categoryId)
    }

    suspend fun getSpecificShopModel(categoryId: String): SpecificShopModel {
        return ApiService.apiInterface.getSpecificShop(categoryId)
    }
}