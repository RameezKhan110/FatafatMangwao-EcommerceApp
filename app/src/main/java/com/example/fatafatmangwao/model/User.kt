package com.example.fatafatmangwao.model

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("name") val name: String? = null ,@SerializedName("email") val email: String? = null,@SerializedName("password") val password: String? = null)
