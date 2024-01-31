package com.example.fatafatmangwao.adapter.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fatafatmangwao.databinding.DiscountCardLayoutBinding
import com.example.fatafatmangwao.model.home.Banners
import com.example.fatafatmangwao.utils.ClickListeners
import com.example.fatafatmangwao.utils.Extensions
import com.example.fatafatmangwao.utils.ListActionTypeClickListener

class HomeBannersAdapter(private val listener: ClickListeners) :
    ListAdapter<Banners, RecyclerView.ViewHolder>(DiffUtil()) {

    var count: Int = 0
    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Banners>() {
        override fun areItemsTheSame(oldItem: Banners, newItem: Banners): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Banners, newItem: Banners): Boolean {
            return oldItem == newItem
        }
    }

    inner class DiscountBannerViewHolder(private val binding: DiscountCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Banners) {
            binding.apply {
                count++
                if(count > 1) {
                    tv2.visibility = View.INVISIBLE
                }
                tvDiscountType.text = "Upto"
                tvDiscountPercent.text = "70%"
                Glide.with(binding.root.context).load(item.banner?.let { Extensions.getImageUrl(it) }).into(ivItem)
                tvShopName.text = "Black and Brown"
                ivItem.setOnClickListener {
                    item._id?.let { it1 ->
                        ListActionTypeClickListener.OnBannerCLicked(
                            it1
                        )
                    }?.let { it2 -> listener.onItemClick(it2) }
                }
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