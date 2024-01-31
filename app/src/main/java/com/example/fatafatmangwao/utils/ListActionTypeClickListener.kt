package com.example.fatafatmangwao.utils

import android.widget.ImageView

sealed class ListActionTypeClickListener {
    data class OnCategoryClicked(val categoryId: String): ListActionTypeClickListener()
    data class OnFavouriteClicked(val ivFav: ImageView, val productId: String, val isFav: Boolean): ListActionTypeClickListener()
    data class OnProductClicked(val productId: String): ListActionTypeClickListener()
    data class OnCartDeleteClicked(val productId: String): ListActionTypeClickListener()
    data class OnHeadingClicked(val heading: String): ListActionTypeClickListener()
    data class OnCartPlusOrMinusClicked(val id: String, val method: String): ListActionTypeClickListener()
    data class OnQRCardClicked(val fromQR: Boolean = false): ListActionTypeClickListener()

    data class OnBannerCLicked(val id: String): ListActionTypeClickListener()
    data class OnSupermarketClicked(val supermarketId: String, val fromSupermarket: Boolean) : ListActionTypeClickListener()
}
