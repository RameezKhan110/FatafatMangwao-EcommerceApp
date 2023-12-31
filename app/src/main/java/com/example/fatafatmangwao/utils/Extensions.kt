package com.example.fatafatmangwao.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.fatafatmangwao.networkmodule.HeaderInterceptor
import com.google.android.material.snackbar.Snackbar
import okhttp3.OkHttpClient

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

    fun storeUserToken(context: Context, token: String) {
        val sharedPref = context.getSharedPreferences("Users_token", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putString("user_token", token)
        editor.apply()
    }

    fun getUserToken(context: Context): String? {
        val sharedPref = context.getSharedPreferences("Users_token", Context.MODE_PRIVATE)

        return sharedPref.getString("user_token", null)
    }

    fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
    }

    fun getImageUrl(imageUrl: String): String {
        val imageEndPoint = imageUrl.substring(16)
        return Constants.IMAGE_BASE_URL+imageEndPoint
    }
}