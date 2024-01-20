package com.example.fatafatmangwao.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fatafatmangwao.R
import com.example.fatafatmangwao.databinding.UserFavItemBinding
import com.example.fatafatmangwao.model.specific_product.RelatedProducts
import com.example.fatafatmangwao.utils.ClickListeners
import com.example.fatafatmangwao.utils.Extensions

class UserSpecificProductAdapter(private val listener: ClickListeners) :
    ListAdapter<RelatedProducts, RecyclerView.ViewHolder>(DiffUtil()) {

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<RelatedProducts>() {
        override fun areItemsTheSame(oldItem: RelatedProducts, newItem: RelatedProducts): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RelatedProducts, newItem: RelatedProducts): Boolean {
            return oldItem == newItem
        }
    }

    inner class FavouriteViewHolder(private val binding: UserFavItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RelatedProducts) {

            binding.apply {
                if(item.images.isNotEmpty()) {
                    Glide.with(binding.root.context).load(Extensions.getImageUrl(item.images.first())).into(iivItem)
                }
                tvItemPrice.text = item.price.toString()
                tvItemTitle.text = item.title
                ivFav.setImageResource(R.drawable.ic_stroke_heart)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavouriteViewHolder(
            UserFavItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as FavouriteViewHolder).bind(item)
    }
}