package com.example.fatafatmangwao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    private val _tokenObserver: MutableLiveData<String> = MutableLiveData()
    val tokenObserver: LiveData<String> = _tokenObserver

    fun getToken(token: String) {
        _tokenObserver.postValue(token)
    }
}