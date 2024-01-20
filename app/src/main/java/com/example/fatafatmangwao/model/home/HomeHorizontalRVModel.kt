package com.example.fatafatmangwao.model.home

sealed class HomeHorizontalRVModel {
    data class BannerModelItem(val bannersItem: List<Banners>): HomeHorizontalRVModel()
}