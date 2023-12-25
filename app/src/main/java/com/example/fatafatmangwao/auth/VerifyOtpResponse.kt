package com.example.fatafatmangwao.auth

data class VerifyOtpResponse(val token: String? = null, val error: Boolean? = null, val message: String? = null)
