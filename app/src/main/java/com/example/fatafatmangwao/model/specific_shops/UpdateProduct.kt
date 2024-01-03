package com.example.fatafatmangwao.model.specific_shops

data class UpdateProduct(
    val _id: String,
    val category: Category,
    val createdAt: String,
    val description: String,
    val featured: Boolean,
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
    val wishlist: Boolean
)