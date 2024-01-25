package com.example.fatafatmangwao.networkmodule

import android.annotation.SuppressLint
import android.app.Application
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.fatafatmangwao.utils.ApplicationClass

object Chucker {
    private val chuckerCollector = ChuckerCollector(
        context = ApplicationClass.getContext(),
        showNotification = false
    )

    @SuppressLint("StaticFieldLeak")
    val chuckerInterceptor = ChuckerInterceptor.Builder(ApplicationClass.application.baseContext)
        .collector(chuckerCollector)
        .build()
}
