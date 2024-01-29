package com.example.fatafatmangwao.model.cart

data class Cart(
    val _id: String? = null,
    val category: String? = null,
    val createdAt: String? = null,
    val description: String? = null,
    val featured: Boolean? = null,
    val id: String? = null,
    val images: List<String>? = null,
    val imageDrawable: Int? = null,
    val price: Int? = null,
    val quantity: Int? = null,
    val rating: Double? = null,
    val ratingCount: Int? = null,
    val shopkeeper: String? = null,
    val status: String? = null,
    val stock: Boolean? = null,
    val title: String? = null,
    val updatedAt: String? = null,
    val views: Int? = null,
    val wishlist: List<String>? = null
)