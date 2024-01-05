package com.example.fatafatmangwao.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fatafatmangwao.R
import com.example.fatafatmangwao.databinding.DiscountCardLayoutBinding
import com.example.fatafatmangwao.databinding.ImageWithCategoryDcVerticalBinding
import com.example.fatafatmangwao.databinding.TwoHorizontalTextLayoutBinding
import com.example.fatafatmangwao.databinding.UserCategoryItemBinding
import com.example.fatafatmangwao.model.home.Banner
import com.example.fatafatmangwao.model.home.Category
import com.example.fatafatmangwao.model.home.HomeVerticalData
import com.example.fatafatmangwao.model.home.PopularShop
import com.example.fatafatmangwao.utils.ClickListeners

class HomeListAdapter(private val listener: ClickListeners) :
    ListAdapter<HomeVerticalData, RecyclerView.ViewHolder>(DiffUtil()) {

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<HomeVerticalData>() {
        override fun areItemsTheSame(
            oldItem: HomeVerticalData,
            newItem: HomeVerticalData
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: HomeVerticalData,
            newItem: HomeVerticalData
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
        return when (getItem(position)) {
            is HomeVerticalData.BannerModelItem -> R.layout.discount_card_layout
            is HomeVerticalData.CategoryModelItem -> R.layout.user_category_item
            is HomeVerticalData.PopularShopItem -> R.layout.image_with_category_dc_vertical
            is HomeVerticalData.ItemHeadingModel -> R.layout.two_horizontal_text_layout
        }
    }

    inner class HomeBannerViewHolder(private val binding: DiscountCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: List<Banner>) {
            binding.apply {

            }

        }
    }

    inner class HomeCategoryViewHolder(private val binding: UserCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: List<Category>) {
            binding.apply {

            }

        }
    }

    inner class ItemsHeadingViewHolder(private val binding: TwoHorizontalTextLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeVerticalData.ItemHeadingModel) {
            binding.apply {

            }

        }
    }

    inner class HomePopularShopViewHolder(private val binding: ImageWithCategoryDcVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: List<PopularShop>) {
            binding.apply {

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.discount_card_layout -> HomeBannerViewHolder(
                DiscountCardLayoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            R.layout.user_category_item -> HomeCategoryViewHolder(
                UserCategoryItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            R.layout.image_with_category_dc_vertical -> HomePopularShopViewHolder(
                ImageWithCategoryDcVerticalBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            R.layout.two_horizontal_text_layout -> ItemsHeadingViewHolder(
                TwoHorizontalTextLayoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> {
                throw IllegalArgumentException("Unknown view type")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is HomeVerticalData.BannerModelItem -> (holder as HomeBannerViewHolder).bind(item.bannerItem)
            is HomeVerticalData.CategoryModelItem -> (holder as HomeCategoryViewHolder).bind(item.categoryItem)
            is HomeVerticalData.PopularShopItem -> (holder as HomePopularShopViewHolder).bind(item.popularShopItem)
            is HomeVerticalData.ItemHeadingModel -> (holder as ItemsHeadingViewHolder).bind(item)
        }

    }
}