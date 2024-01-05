package com.example.fatafatmangwao.adapter.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fatafatmangwao.databinding.DiscountCardLayoutBinding
import com.example.fatafatmangwao.model.home.Banner
import com.example.fatafatmangwao.utils.ClickListeners

class HomeBannersAdapter :
    ListAdapter<Banner, RecyclerView.ViewHolder>(DiffUtil()) {

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }
    }

    inner class DiscountBannerViewHolder(private val binding: DiscountCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Banner) {
            binding.apply {
                tvDiscountType.text = item.discountType
                tvDiscountPercent.text = item.discountPercent + "%"
                Glide.with(binding.root.context).load(item.shopImage).into(ivItem)
                tvShopName.text = item.shopName
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DiscountBannerViewHolder(
            DiscountCardLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as DiscountBannerViewHolder).bind(item)
    }
}