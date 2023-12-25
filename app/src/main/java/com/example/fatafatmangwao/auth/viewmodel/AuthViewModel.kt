package com.example.fatafatmangwao.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fatafatmangwao.OtpData
import com.example.fatafatmangwao.auth.RegistrationResponse
import com.example.fatafatmangwao.auth.ResendOtpResponse
import com.example.fatafatmangwao.auth.Resource
import com.example.fatafatmangwao.auth.User
import com.example.fatafatmangwao.auth.VerifyOtpResponse
import com.example.fatafatmangwao.auth.repository.AuthRepository
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _registerUserObserver: MutableLiveData<Resource<RegistrationResponse>> =
        MutableLiveData()
    val registerUserObserver: LiveData<Resource<RegistrationResponse>> = _registerUserObserver

    private val _loginUserObserver: MutableLiveData<Resource<RegistrationResponse>> =
        MutableLiveData()
    val loginUserObserver: LiveData<Resource<RegistrationResponse>> = _loginUserObserver

    private val _verifyOtpObserver: MutableLiveData<Resource<VerifyOtpResponse>> = MutableLiveData()
    val verifyOtpObserver: LiveData<Resource<VerifyOtpResponse>> = _verifyOtpObserver

    private val _resendOtpObserver: MutableLiveData<Resource<ResendOtpResponse>> = MutableLiveData()
    val resendOtpObserver: LiveData<Resource<ResendOtpResponse>> = _resendOtpObserver
    fun registerUser(registrationDetails: User) = viewModelScope.launch {
        _registerUserObserver.postValue(Resource.Loading())
        try {
            val response = authRepository.registerUser(registrationDetails)
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
            val response = authRepository.verifyOtp(otp)
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
            val response = authRepository.resendOtp()
            _resendOtpObserver.postValue(Resource.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            _resendOtpObserver.postValue (e.localizedMessage?.let { Resource.Error(it) })

        }
    }

    fun loginUser(loginDetails: User) = viewModelScope.launch {
        _registerUserObserver.postValue(Resource.Loading())
        try {
            val response = authRepository.registerUser(loginDetails)
            if (response.error?.not() == true)
                _registerUserObserver.postValue(Resource.Success(response))
            else {
                _registerUserObserver.postValue(response.message?.let { Resource.Error(it, null) })
            }
        } catch (e: Exception) {
            _registerUserObserver.postValue(e.localizedMessage?.let { Resource.Error(it) })
        }
    }
}