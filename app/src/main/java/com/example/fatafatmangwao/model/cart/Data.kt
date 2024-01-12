package com.example.fatafatmangwao.model.cart

data class Data(
    val cart: List<Cart>,
    val delivery: Int,
    val relatedProducts: List<Any>,
    val subTotal: Int,
    val total: Int
)