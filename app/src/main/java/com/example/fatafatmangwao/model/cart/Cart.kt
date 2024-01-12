package com.example.fatafatmangwao.model.cart

data class Cart(
    val _id: String,
    val category: String,
    val createdAt: String,
    val description: String,
    val featured: Boolean,
    val id: String,
    val images: List<String>,
    val price: Int,
    val quantity: Int,
    val rating: Double,
    val ratingCount: Int,
    val shopkeeper: String,
    val status: String,
    val stock: Boolean,
    val title: String,
    val updatedAt: String,
    val views: Int,
    val wishlist: List<String>
)