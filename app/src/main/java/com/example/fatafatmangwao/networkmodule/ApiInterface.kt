package com.example.fatafatmangwao.networkmodule

import com.example.fatafatmangwao.utils.Extensions
import com.example.fatafatmangwao.OtpData
import com.example.fatafatmangwao.model.GeneralResponse
import com.example.fatafatmangwao.model.ProductRequest
import com.example.fatafatmangwao.model.category.CategoryModel
import com.example.fatafatmangwao.model.RegistrationResponse
import com.example.fatafatmangwao.model.ResendOtpResponse
import com.example.fatafatmangwao.model.UpdateProductRequest
import com.example.fatafatmangwao.model.User
import com.example.fatafatmangwao.model.VerifyOtpResponse
import com.example.fatafatmangwao.model.cart.GetCartModel
import com.example.fatafatmangwao.model.home.HomeModel
import com.example.fatafatmangwao.model.shops.ShopsModel
import com.example.fatafatmangwao.model.specific_product.SpecificProductModel
import com.example.fatafatmangwao.model.specific_shops.SpecificShopModel
import com.example.fatafatmangwao.model.specific_shops.UpdateProduct
import com.example.fatafatmangwao.model.wishlist.AddWishListModel
import com.example.fatafatmangwao.model.wishlist.GetWishListModel
import com.example.fatafatmangwao.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApiInterface {

    @POST("user/register")
    suspend fun registerUser(@Body registrationDetails: User): RegistrationResponse

    @POST("user/verify")
    suspend fun verifyOtp(@Body otp: OtpData): VerifyOtpResponse

    @GET("user/otp")
    suspend fun resendOtp(): ResendOtpResponse

    @POST("user/login")
    suspend fun loginUser(@Body loginDetails: User): RegistrationResponse

    @GET("category?limit=2")
    suspend fun getCategories(@Query("limit") limit: Int): CategoryModel

    @GET("shop/get?")
    suspend fun getAllShops(@Query("search") shopName: String, @Query("category") categoryId: String): ShopsModel

    @GET("shop/get/{id}")
    suspend fun getSpecificShop(@Path ("id") categoryId: String): SpecificShopModel

    @GET("product/wishlist/{id}")
    suspend fun addToFavourite(@Path ("id") productId: String): AddWishListModel

    @GET("product/wishlist")
    suspend fun getFavourite(): GetWishListModel

    @GET("product/{id}")
    suspend fun getSpecificProduct(@Path ("id") productId: String): SpecificProductModel

    @GET("home")
    suspend fun getHomeDetails(): HomeModel

    @POST("cart")
    suspend fun addToCart(@Body request: ProductRequest): GeneralResponse

    @GET("cart")
    suspend fun getCart(): GetCartModel

    @DELETE("cart/{id}")
    suspend fun deleteFromCart(@Path("id") productId: String): GeneralResponse

    @POST("order")
    suspend fun placeOrder(): GeneralResponse

    @PUT("cart/{id}")
    suspend fun updateProductQuantity(@Path("id") productId: String, @Body method: UpdateProductRequest): GeneralResponse
}

object ApiService {

    val apiInterface: AuthApiInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(Extensions.createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiInterface = retrofit.create()
    }
}
