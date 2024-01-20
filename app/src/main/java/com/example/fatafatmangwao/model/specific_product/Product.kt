package com.example.fatafatmangwao.model.specific_product

data class Product(
    val _id: String,
    val category: Category,
    val createdAt: String,
    val description: String,
    val featured: Boolean,
    val id: String,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val ratingCount: Int,
    val shopkeeper: String,
    val status: String,
    val stock: Boolean,
    val title: String,
    val updatedAt: String,
    val views: Int,
    val wishlist: List<Any>
)