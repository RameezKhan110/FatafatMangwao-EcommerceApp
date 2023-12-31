package com.example.fatafatmangwao.adapter

sealed class ClickListeners {
    data class OnCategoryClicked(val categoryId: String): ClickListeners()
}
