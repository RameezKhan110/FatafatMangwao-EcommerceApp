package com.example.fatafatmangwao.utils

import android.widget.ImageView

sealed class ListActionTypeClickListener {
    data class OnCategoryClicked(val categoryId: String): ListActionTypeClickListener()
    data class OnFavouriteClicked(val ivFav: ImageView, val productId: String, val isFav: Boolean): ListActionTypeClickListener()
    data class OnProductClicked(val productId: String): ListActionTypeClickListener()
}
