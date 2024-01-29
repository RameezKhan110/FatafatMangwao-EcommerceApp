package com.example.fatafatmangwao.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fatafatmangwao.model.User
import com.example.fatafatmangwao.networkmodule.Chucker
import com.example.fatafatmangwao.networkmodule.HeaderInterceptor
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object Extensions {

    fun Context.showToast(message: String, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }

    fun View.showSnackBar(
        message: String,
        duration: Int,
        actionListener: ((View) -> Unit)? = null
    ) {
        val snackBar = Snackbar.make(this, message, duration)

        snackBar.setAction("Dismiss") { view ->
            actionListener?.invoke(view)
            snackBar.dismiss()
        }

        snackBar.show()
    }

    fun View.autoDisableSnackBar(
        message: String,
        duration: Int,
    ) {
        val snackBar = Snackbar.make(this, message, duration)
        snackBar.show()
    }

    fun storeUserToken(context: Context, token: String?) {
        val sharedPref = context.getSharedPreferences("Users_token", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putString("user_token", token)
        editor.apply()
    }

    fun getUserToken(context: Context): String? {
        val sharedPref = context.getSharedPreferences("Users_token", Context.MODE_PRIVATE)

        return sharedPref.getString("user_token", null)
    }

    fun clearUserToken(context: Context) {
        val sharedPref = context.getSharedPreferences("Users_token", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.remove("user_token")
        editor.apply()
    }
    fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(Chucker.chuckerInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }

    suspend fun mockLoading() {
        delay(3000)

    }

    fun getImageUrl(imageUrl: String): String {
        val imageEndPoint = imageUrl.substring(16)
        return Constants.IMAGE_BASE_URL + imageEndPoint
    }

    fun isFirstLaunch(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences("launch_checker", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("is_first_launch", true)
    }

    fun markAppLaunched(context: Context) {
        val preferences = context.getSharedPreferences("launch_checker", Context.MODE_PRIVATE)
        preferences.edit().putBoolean("is_first_launch", false).apply()
    }

    fun <T> clearLiveDataValue(liveData: LiveData<T>) {
        (liveData as MutableLiveData).value = null
    }

    fun View.visible() {
        visibility = View.VISIBLE
    }

    fun View.gone() {
        visibility = View.GONE
    }

    @SuppressLint("CommitPrefEdits")
    fun saveUserDetails(context: Context, userDetails: User) {
        val sharedPref = context.getSharedPreferences("User_Detail", Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonObj = gson.toJson(userDetails)
        val editor = sharedPref.edit().putString("user_details", jsonObj).apply()

    }

    fun getUserDetails(context: Context): User? {
        val sharedPref = context.getSharedPreferences("User_Detail", Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonObj = sharedPref.getString("user_details", null)
        return gson.fromJson(jsonObj, User::class.java)


    }
}