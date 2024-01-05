package com.example.fatafatmangwao.model.home

sealed class HomeHorizontalRVModel {
    data class BannerModelItem(val bannerItem: List<Banner>): HomeHorizontalRVModel()
}