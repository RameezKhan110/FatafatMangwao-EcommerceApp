package com.example.fatafatmangwao.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    private val _tokenObserver: MutableLiveData<String> = MutableLiveData()
    var shopId: String? = null
    var productId: String? = null
    var categoryId: String? = null
}