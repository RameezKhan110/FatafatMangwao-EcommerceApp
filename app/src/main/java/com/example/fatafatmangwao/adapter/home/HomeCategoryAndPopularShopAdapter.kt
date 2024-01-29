package com.example.fatafatmangwao.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fatafatmangwao.R
import com.example.fatafatmangwao.databinding.HomCategoryGridListLayoutBinding
import com.example.fatafatmangwao.databinding.HomePopularGridListLayoutBinding
import com.example.fatafatmangwao.databinding.SupermarketsCardLayoutBinding
import com.example.fatafatmangwao.databinding.TwoHorizontalTextLayoutBinding
import com.example.fatafatmangwao.model.home.Category
import com.example.fatafatmangwao.model.home.HomeVerticalRVModel
import com.example.fatafatmangwao.model.home.PopularShop
import com.example.fatafatmangwao.utils.ClickListeners
import com.example.fatafatmangwao.utils.ListActionTypeClickListener

class HomeCategoryAndPopularShopAdapter(val listener: ClickListeners) :
    ListAdapter<HomeVerticalRVModel, RecyclerView.ViewHolder>(DiffUtil()) {

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<HomeVerticalRVModel>() {
        override fun areItemsTheSame(
            oldItem: HomeVerticalRVModel,
            newItem: HomeVerticalRVModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: HomeVerticalRVModel,
            newItem: HomeVerticalRVModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return when (getItem(position)) {
            is HomeVerticalRVModel.ItemHeadingModel -> R.layout.two_horizontal_text_layout
            is HomeVerticalRVModel.CategoryModelItem -> R.layout.hom_category_grid_list_layout
            is HomeVerticalRVModel.PopularShopItem -> R.layout.home_popular_grid_list_layout
            is HomeVerticalRVModel.SupermarketItemModel -> R.layout.supermarkets_card_layout
            else -> {
                throw IllegalArgumentException("invalid position")
            }
        }
    }

    inner class ItemsHeadingViewHolder(private val binding: TwoHorizontalTextLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeVerticalRVModel.ItemHeadingModel) {
            binding.apply {
                tvHeadingName.text = item.heading
                label.text = item.label
                label.setOnClickListener {
                    listener.onItemClick(ListActionTypeClickListener.OnHeadingClicked(item.heading))
                }
            }

        }
    }

    inner class HomeCategoryViewHolder(private val binding: HomCategoryGridListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: List<Category>) {
            binding.apply {
                val adapter = HomeCateogriesAdapter()
                rvList.layoutManager = GridLayoutManager(binding.root.context, 3)
                rvList.adapter = adapter

                adapter.submitList(item)
            }

        }
    }

    inner class HomePopularShopViewHolder(private val binding: HomePopularGridListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: List<PopularShop>) {
            binding.apply {
                val adapter = HomePopularShopsAdapter()
                rvList.layoutManager = GridLayoutManager(binding.root.context, 2)
                rvList.adapter = adapter
                adapter.submitList(item)
            }

        }
    }

    inner class SupermarketCardViewHolder(private val binding: SupermarketsCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeVerticalRVModel.SupermarketItemModel) {
            binding.apply {
                btnViewAll.setOnClickListener {
                    listener.onItemClick(ListActionTypeClickListener.OnQRCardClicked(true))
                }
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.hom_category_grid_list_layout -> HomeCategoryViewHolder(
                HomCategoryGridListLayoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            R.layout.home_popular_grid_list_layout -> HomePopularShopViewHolder(
                HomePopularGridListLayoutBinding.inflate(
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

            R.layout.supermarkets_card_layout -> SupermarketCardViewHolder(
                SupermarketsCardLayoutBinding.inflate(
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
            is HomeVerticalRVModel.CategoryModelItem -> (holder as HomeCategoryViewHolder).bind(item.categoryItem)
            is HomeVerticalRVModel.PopularShopItem -> (holder as HomePopularShopViewHolder).bind(
                item.popularShopItem
            )

            is HomeVerticalRVModel.ItemHeadingModel -> (holder as ItemsHeadingViewHolder).bind(item)
            is HomeVerticalRVModel.SupermarketItemModel -> (holder as SupermarketCardViewHolder).bind(
                item
            )
        }

    }
}