package com.example.fatafatmangwao.auth

data class RegistrationResponse(val token: String? = null, val error: Boolean? = null, val message: String? = null)