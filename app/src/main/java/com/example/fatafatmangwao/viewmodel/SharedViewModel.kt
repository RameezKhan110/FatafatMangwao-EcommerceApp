package com.example.fatafatmangwao.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fatafatmangwao.model.cart.Cart

class SharedViewModel: ViewModel() {

    var shopId: String? = null
    var productId: String? = null
    var categoryId: String? = null
    var productQuantity: Int? = null
    var supermarketId: String? = null

    val supermarketCartItems = arrayListOf<Cart>()
}