package com.example.fatafatmangwao.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    private val _tokenObserver: MutableLiveData<String> = MutableLiveData()
    var categoryId: String? = null
    val tokenObserver: LiveData<String> = _tokenObserver

    fun getToken(token: String) {
        _tokenObserver.postValue(token)
    }
}