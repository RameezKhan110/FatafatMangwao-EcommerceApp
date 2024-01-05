package com.example.fatafatmangwao.model.home

sealed class HomeVerticalData {
    data class BannerModelItem(val bannerItem: List<Banner>): HomeVerticalData()
    data class CategoryModelItem(val categoryItem: List<Category>): HomeVerticalData()
    data class PopularShopItem(val popularShopItem: List<PopularShop>): HomeVerticalData()
    data class ItemHeadingModel(val heading: String, val label: String): HomeVerticalData()
}