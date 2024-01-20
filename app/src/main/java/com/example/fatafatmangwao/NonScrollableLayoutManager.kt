package com.example.fatafatmangwao

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class NonScrollableLayoutManager(context: Context) : LinearLayoutManager(context) {

    override fun canScrollVertically(): Boolean {
        return false
    }

    override fun canScrollHorizontally(): Boolean {
        return false
    }
}