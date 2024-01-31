package com.example.fatafatmangwao.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fatafatmangwao.R
import com.example.fatafatmangwao.databinding.CategoryPopularShopsRvHomeLayoutBinding
import com.example.fatafatmangwao.databinding.DiscountCardRvHomeLayoutBinding
import com.example.fatafatmangwao.model.home.HomeData
import com.example.fatafatmangwao.model.home.HomeHorizontalRVModel
import com.example.fatafatmangwao.model.home.HomeVerticalRVModel
import com.example.fatafatmangwao.utils.ClickListeners
import com.example.fatafatmangwao.utils.ListActionTypeClickListener

class HomeListAdapter(private val listener: ClickListeners) :
    ListAdapter<HomeData, RecyclerView.ViewHolder>(DiffUtil()), ClickListeners{

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<HomeData>() {
        override fun areItemsTheSame(
            oldItem: HomeData,
            newItem: HomeData
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: HomeData,
            newItem: HomeData
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return when (getItem(position)) {
            is HomeData.homeHorizontalItems -> R.layout.discount_card_rv_home_layout
            is HomeData.homeVerticalItems -> R.layout.category_popular_shops_rv_home_layout

        }
    }

    inner class HomeBannerRvViewHolder(private val binding: DiscountCardRvHomeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeHorizontalRVModel.BannerModelItem) {
            binding.apply {
                val adapter = HomeBannersAdapter(this@HomeListAdapter)
                rvList.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
                rvList.adapter = adapter

                adapter.submitList(item.bannersItem)
            }

        }
    }

    inner class HomeVerticalRvViewHolder(private val binding: CategoryPopularShopsRvHomeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeVerticalRVModel) {
            binding.apply {

                val adapter = HomeCategoryAndPopularShopAdapter(this@HomeListAdapter)
                rvList.layoutManager = LinearLayoutManager(binding.root.context)
                rvList.adapter = adapter

                adapter.submitList(listOf(item))
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.discount_card_rv_home_layout -> HomeBannerRvViewHolder(
                DiscountCardRvHomeLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            R.layout.category_popular_shops_rv_home_layout -> HomeVerticalRvViewHolder(
                CategoryPopularShopsRvHomeLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> {
                throw IllegalArgumentException("invalid view type")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is HomeData.homeHorizontalItems -> {
                (holder as HomeBannerRvViewHolder).bind(item.horizontalItems)
            }

            is HomeData.homeVerticalItems -> {
                (holder as HomeVerticalRvViewHolder).bind(item.homeVerticalItems)
            }
        }

    }

    override fun onItemClick(clickListener: ListActionTypeClickListener) {
        when(clickListener) {
            is ListActionTypeClickListener.OnHeadingClicked -> {
                listener.onItemClick(ListActionTypeClickListener.OnHeadingClicked(clickListener.heading))
            }
            is ListActionTypeClickListener.OnQRCardClicked -> {
                listener.onItemClick(ListActionTypeClickListener.OnQRCardClicked(clickListener.fromQR))
            }

            is ListActionTypeClickListener.OnBannerCLicked -> {
                listener.onItemClick(ListActionTypeClickListener.OnBannerCLicked(clickListener.id))
            }

            else -> {

            }
        }
    }
}