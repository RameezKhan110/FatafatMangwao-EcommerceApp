package com.example.fatafatmangwao.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fatafatmangwao.OtpData
import com.example.fatafatmangwao.model.category.CategoryModel
import com.example.fatafatmangwao.model.RegistrationResponse
import com.example.fatafatmangwao.model.ResendOtpResponse
import com.example.fatafatmangwao.utils.Resource
import com.example.fatafatmangwao.model.User
import com.example.fatafatmangwao.model.VerifyOtpResponse
import com.example.fatafatmangwao.repository.ApiRepository
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._getCategoryObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._getShopsObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._getSpecificShopObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._loginUserObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._registerUserObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._resendOtpObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._verifyOtpObserver
import kotlinx.coroutines.launch

class ActivityViewModel : ViewModel() {

    private val apiRepository = ApiRepository()

    fun registerUser(registrationDetails: User) = viewModelScope.launch {

        _registerUserObserver.postValue(Resource.Loading())
        try {
            val response = apiRepository.registerUser(registrationDetails)
            if (response.error?.not() == true)
                _registerUserObserver.postValue(Resource.Success(response))
            else {
                _registerUserObserver.postValue(response.message?.let { Resource.Error(it, null) })
            }
        } catch (e: Exception) {
            _registerUserObserver.postValue(e.localizedMessage?.let { Resource.Error(it) })
        }
    }

    fun verifyOtp(otp: OtpData) = viewModelScope.launch {
        _verifyOtpObserver.postValue(Resource.Loading())
        try {
            val response = apiRepository.verifyOtp(otp)
            if (response.error?.not() == true) {
                _verifyOtpObserver.postValue(Resource.Success(response))

            } else {
                _verifyOtpObserver.postValue(response.message?.let { Resource.Error(it) })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _verifyOtpObserver.postValue(e.message?.let { Resource.Error(it) })
        }
    }

    fun resendOtp() = viewModelScope.launch {
        _resendOtpObserver.postValue(Resource.Loading())
        try {
            val response = apiRepository.resendOtp()
            _resendOtpObserver.postValue(Resource.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            _resendOtpObserver.postValue(e.localizedMessage?.let { Resource.Error(it) })

        }
    }

    fun loginUser(loginDetails: User) = viewModelScope.launch {
        _loginUserObserver.postValue(Resource.Loading())
        try {
            val response = apiRepository.loginUser(loginDetails)
            if (response.error?.not() == true)
                _loginUserObserver.postValue(Resource.Success(response))
            else {
                _loginUserObserver.postValue(response.message?.let { Resource.Error(it, null) })
            }
        } catch (e: Exception) {
            _loginUserObserver.postValue(e.localizedMessage?.let { Resource.Error(it) })
        }
    }

    fun getCategories(limit: Int) = viewModelScope.launch {
        _getCategoryObserver.postValue(Resource.Loading())
        try {
            val response = apiRepository.getCategories(limit)
            if (response.error.not())
                _getCategoryObserver.postValue(Resource.Success(response))
        } catch (e: Exception) {
            _getCategoryObserver.postValue(e.localizedMessage?.let { Resource.Error(it) })
        }
    }

    fun getAllShops(shopName: String, categoryId: String) = viewModelScope.launch {
        _getShopsObserver.postValue(Resource.Loading())
        try {
            val response = apiRepository.getAllShops(shopName, categoryId)
            if (response.error.not())
                _getShopsObserver.postValue(Resource.Success(response))
        } catch (e: Exception) {
            _getShopsObserver.postValue(e.localizedMessage?.let { Resource.Error(it) })
        }
    }

    fun getSpecificShop(categoryId: String) = viewModelScope.launch {
        _getSpecificShopObserver.postValue(Resource.Loading())
        try {
            val response = apiRepository.getSpecificShopModel(categoryId)
            if (response.error.not()) {
                _getSpecificShopObserver.postValue(Resource.Success(response))
            }
//            } else {
//                _getSpecificShopObserver.postValue(response.error.let { Resource.Error(it, null) })
//            }
        } catch (e: Exception) {
            _getSpecificShopObserver.postValue(e.cause?.let { Resource.Error(it.message.toString()) })
        }
    }
}