package com.example.fatafatmangwao.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.fatafatmangwao.databinding.UserCategoryItemBinding
import com.example.fatafatmangwao.databinding.UserPopularshopsItemBinding
import com.example.fatafatmangwao.model.category.Data
import com.example.fatafatmangwao.utils.Constants
import com.example.fatafatmangwao.utils.Extensions

class UserShopsAdapter: ListAdapter<com.example.fatafatmangwao.model.shops.Data, RecyclerView.ViewHolder>(DiffUtil()) {

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<com.example.fatafatmangwao.model.shops.Data>() {
        override fun areItemsTheSame(oldItem: com.example.fatafatmangwao.model.shops.Data, newItem: com.example.fatafatmangwao.model.shops.Data): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: com.example.fatafatmangwao.model.shops.Data, newItem: com.example.fatafatmangwao.model.shops.Data): Boolean {
            return oldItem == newItem
        }
    }

    inner class ShopsViewHolder(binding: UserPopularshopsItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShopsViewHolder(UserPopularshopsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        UserPopularshopsItemBinding.bind(holder.itemView).apply {

            tvName.text = item.shopname
            tvMinimumAmount.text = "250"
            deliveryTimeTv.text = "30 min"
            tvDeliveryCharges.text  = "200 Rs."
            tvAddress.text = "Near Akram Biryani House Latifabad Unit 11 Hyderabad"

            Glide.with(holder.itemView.context)
                .load(Extensions.getImageUrl(item.cover))
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("Glide", "Image loaded successfully")
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("Glide", "Image load failed: ${e?.message}")
                        return false
                    }
                })
                .into(itemPicImg)
        }
    }
}