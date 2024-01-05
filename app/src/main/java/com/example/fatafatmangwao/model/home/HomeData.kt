package com.example.fatafatmangwao.model.home

sealed class HomeData {
    data class homeHorizontalItems(val horizontalItems: HomeHorizontalRVModel.BannerModelItem): HomeData()
    data class homeVerticalItems(val homeVerticalItems: HomeVerticalRVModel): HomeData()
}
