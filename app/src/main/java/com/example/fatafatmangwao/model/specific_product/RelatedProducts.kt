package com.example.fatafatmangwao.model.specific_product

data class RelatedProducts(

    var _id: String? = null,
    var shopkeeper: String? = null,
    var title: String? = null,
    var category: Category? = Category(),
    var description: String? = null,
    var price: Int? = null,
    var images: ArrayList<String> = arrayListOf(),
    var views: Int? = null,
    var featured: Boolean? = null,
    var rating: Int? = null,
    var ratingCount: Int? = null,
    var status: String? = null,
    var stock: Boolean? = null,
    var wishlist: ArrayList<String> = arrayListOf(),
    var createdAt: String? = null,
    var updatedAt: String? = null,
    var id: String? = null

)