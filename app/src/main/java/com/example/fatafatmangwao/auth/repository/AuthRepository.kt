package com.example.fatafatmangwao.auth.repository

import com.example.fatafatmangwao.OtpData
import com.example.fatafatmangwao.auth.AuthApiInterface
import com.example.fatafatmangwao.auth.AuthApiService
import com.example.fatafatmangwao.auth.RegistrationResponse
import com.example.fatafatmangwao.auth.ResendOtpResponse
import com.example.fatafatmangwao.auth.User
import com.example.fatafatmangwao.auth.VerifyOtpResponse

class AuthRepository {

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
}


// {
//    "token": null,
//    "error": true,
//    "message": "All Feilds Are Required"
//}