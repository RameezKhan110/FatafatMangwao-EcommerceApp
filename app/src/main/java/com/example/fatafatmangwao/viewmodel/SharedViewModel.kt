package com.example.fatafatmangwao.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    var shopId: String? = null
    var productId: String? = null
    var categoryId: String? = null
    var productQuantity: Int? = null
}