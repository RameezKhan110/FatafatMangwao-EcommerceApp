package com.example.fatafatmangwao.model.home

sealed class HomeVerticalRVModel {

    data class CategoryModelItem(val categoryItem: List<Category>): HomeVerticalRVModel()
    data class PopularShopItem(val popularShopItem: List<PopularShop>): HomeVerticalRVModel()
    data class ItemHeadingModel(val heading: String, val label: String): HomeVerticalRVModel()
}
