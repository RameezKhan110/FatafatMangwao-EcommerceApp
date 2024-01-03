package com.example.fatafatmangwao.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    private val _tokenObserver: MutableLiveData<String> = MutableLiveData()
    var categoryId: String? = null
    var productId: String? = null
}