package com.example.fatafatmangwao.utils

sealed class ListActionTypeClickListener {
    data class OnCategoryClicked(val categoryId: String): ListActionTypeClickListener()
}
