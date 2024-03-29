package com.example.fatafatmangwao.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fatafatmangwao.OtpData
import com.example.fatafatmangwao.model.ProductRequest
import com.example.fatafatmangwao.model.UpdateProductRequest
import com.example.fatafatmangwao.model.User
import com.example.fatafatmangwao.model.home.HomeData
import com.example.fatafatmangwao.repository.ApiRepository
import com.example.fatafatmangwao.utils.Resource
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._addToCartObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._addToFavouriteObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._deleteFromCartObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._getCartObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._getCategoryObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._getFavouriteObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._getHomeDetailsObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._getShopsObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._getSpecificProductObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._getSpecificShopObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._loginUserObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._placeOrderObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._registerUserObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._resendOtpObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._updateProductQuantityObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers._verifyOtpObserver
import kotlinx.coroutines.launch

class ActivityViewModel : ViewModel() {

    private val apiRepository = ApiRepository()
    var homeData: ArrayList<HomeData>? = null

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
        } catch (e: Exception) {
            _getSpecificShopObserver.postValue(e.cause?.let { Resource.Error(it.message.toString()) })
        }
    }


    fun addToFavourite(productId: String) = viewModelScope.launch {
        _addToFavouriteObserver.postValue(Resource.Loading())
        try {
            val response = apiRepository.addToFavourite(productId)
            if (response.error.not()) {
                _addToFavouriteObserver.postValue(Resource.Success(response))
            }
        } catch (e: Exception) {
            _addToFavouriteObserver.postValue(e.cause?.let { Resource.Error(it.message.toString()) })
        }
    }

    fun getFavourite() = viewModelScope.launch {
        _getFavouriteObserver.postValue(Resource.Loading())
        try {
            val response = apiRepository.getFavourite()
            if (response.error.not()) {
                _getFavouriteObserver.postValue(Resource.Success(response))
            }
        } catch (e: Exception) {
            _getFavouriteObserver.postValue(e.cause?.let { Resource.Error(it.message.toString()) })
        }
    }

    fun getSpecificProduct(productId: String) = viewModelScope.launch {
        _getSpecificProductObserver.postValue(Resource.Loading())
        try {
            val response = apiRepository.getSpecificProduct(productId)
            if (response.error.not()) {
                _getSpecificProductObserver.postValue(Resource.Success(response))
            }
        } catch (e: Exception) {
            _getSpecificProductObserver.postValue(e.cause?.let { Resource.Error(it.message.toString()) })
        }
    }

    fun getHomeDetails() = viewModelScope.launch {
        _getHomeDetailsObserver.postValue(Resource.Loading())
        try {
            val response = apiRepository.getHomeDetails()
            if (response.error.not()) {
                _getHomeDetailsObserver.postValue(Resource.Success(response))
            }
        } catch (e: Exception) {
            _getHomeDetailsObserver.postValue(e.cause?.let { Resource.Error(it.message.toString()) })
        }
    }

    fun addToCart(request: ProductRequest) = viewModelScope.launch {
        _addToCartObserver.postValue(Resource.Loading())
        try {
            val response = apiRepository.addToCart(request)
            if (response.error?.not() == true) {
                _addToCartObserver.postValue(Resource.Success(response))
            } else {
                _addToCartObserver.postValue(response.message?.let { Resource.Error(it) })
            }
        } catch (e: Exception) {
            _addToCartObserver.postValue(e.cause?.let { Resource.Error(it.message.toString()) })
        }
    }

    fun getCart() = viewModelScope.launch {
        _getCartObserver.postValue(Resource.Loading())
        try {
            val response = apiRepository.getCart()
            if (response.error.not()) {
                _getCartObserver.postValue(Resource.Success(response))
            } else {
                _getCartObserver.postValue(response.error.let { Resource.Error(it.toString()) })
            }
        } catch (e: Exception) {
            _getCartObserver.postValue(e.cause?.let { Resource.Error(it.message.toString()) })
        }
    }

    fun deleteFromCart(productId: String) = viewModelScope.launch {
        _deleteFromCartObserver.postValue(Resource.Loading())
        try {
            val response = apiRepository.deleteFromCart(productId)
            if (response.error?.not() == true) {
                _deleteFromCartObserver.postValue(Resource.Success(response))
                getCart()
            } else {
                _deleteFromCartObserver.postValue(response.error.let { Resource.Error(it.toString()) })
            }
        } catch (e: Exception) {
            _deleteFromCartObserver.postValue(e.cause?.let { Resource.Error(it.message.toString()) })
        }
    }

    fun placeOrder() = viewModelScope.launch {
        _placeOrderObserver.postValue(Resource.Loading())
        try {
            val response = apiRepository.placeOrder()
            if (response.error?.not() == true) {
                _placeOrderObserver.postValue(Resource.Success(response))
            } else {
                _placeOrderObserver.postValue(response.error.let { Resource.Error(it.toString()) })
            }
        } catch (e: Exception) {
            _placeOrderObserver.postValue(e.cause?.let { Resource.Error(it.message.toString()) })
        }
    }

    fun updateProductQuantity(id: String, method: UpdateProductRequest) = viewModelScope.launch {
        _updateProductQuantityObserver.postValue(Resource.Loading())
        try {
            val response = apiRepository.updateProductQuantity(id, method)
            if (response.error?.not() == true) {
                _updateProductQuantityObserver.postValue(Resource.Success(response))
                getCart()
            } else {
                _updateProductQuantityObserver.postValue(response.error.let { Resource.Error(it.toString()) })
            }
        } catch (e: Exception) {
            _updateProductQuantityObserver.postValue(e.cause?.let { Resource.Error(it.message.toString()) })
        }
    }
}