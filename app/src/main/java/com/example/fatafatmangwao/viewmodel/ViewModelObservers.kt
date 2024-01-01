package com.example.fatafatmangwao.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fatafatmangwao.model.RegistrationResponse
import com.example.fatafatmangwao.model.ResendOtpResponse
import com.example.fatafatmangwao.model.VerifyOtpResponse
import com.example.fatafatmangwao.model.category.CategoryModel
import com.example.fatafatmangwao.model.shops.ShopsModel
import com.example.fatafatmangwao.model.specific_shops.SpecificShopModel
import com.example.fatafatmangwao.utils.Resource

object ViewModelObservers {

    val _registerUserObserver: MutableLiveData<Resource<RegistrationResponse>> =
        MutableLiveData()
    val registerUserObserver: LiveData<Resource<RegistrationResponse>> = _registerUserObserver

    val _loginUserObserver: MutableLiveData<Resource<RegistrationResponse>> =
        MutableLiveData()
    val loginUserObserver: LiveData<Resource<RegistrationResponse>> = _loginUserObserver

    val _verifyOtpObserver: MutableLiveData<Resource<VerifyOtpResponse>> = MutableLiveData()
    val verifyOtpObserver: LiveData<Resource<VerifyOtpResponse>> = _verifyOtpObserver

    val _resendOtpObserver: MutableLiveData<Resource<ResendOtpResponse>> = MutableLiveData()
    val resendOtpObserver: LiveData<Resource<ResendOtpResponse>> = _resendOtpObserver

    val _getCategoryObserver: MutableLiveData<Resource<CategoryModel>> = MutableLiveData()
    val getCategoryObserver: LiveData<Resource<CategoryModel>> = _getCategoryObserver

    val _getShopsObserver: MutableLiveData<Resource<ShopsModel>> = MutableLiveData()
    val getShopsObserver: LiveData<Resource<ShopsModel>> = _getShopsObserver

    val _getSpecificShopObserver: MutableLiveData<Resource<SpecificShopModel>> = MutableLiveData()
    val getSpecificShopObserver: LiveData<Resource<SpecificShopModel>> = _getSpecificShopObserver
}