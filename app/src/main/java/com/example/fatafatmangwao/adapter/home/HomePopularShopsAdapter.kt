package com.example.fatafatmangwao.adapter.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fatafatmangwao.databinding.ImageWithCategoryDcVerticalBinding
import com.example.fatafatmangwao.model.home.PopularShop
import com.example.fatafatmangwao.utils.Extensions

class HomePopularShopsAdapter :
    ListAdapter<PopularShop, RecyclerView.ViewHolder>(DiffUtil()) {

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<PopularShop>() {
        override fun areItemsTheSame(oldItem: PopularShop, newItem: PopularShop): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PopularShop, newItem: PopularShop): Boolean {
            return oldItem == newItem
        }
    }

    inner class PopularShopViewHolder(private val binding: ImageWithCategoryDcVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: PopularShop) {
            binding.apply {
                Glide.with(binding.root.context).load(Extensions.getImageUrl(item.profile)).into(ivItem)
                tvShopName.text = item.name
                tvCategory.text = item.category
                tvDc.text = "PKR.200 delivery fees"
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PopularShopViewHolder(
            ImageWithCategoryDcVerticalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as PopularShopViewHolder).bind(item)
    }
}