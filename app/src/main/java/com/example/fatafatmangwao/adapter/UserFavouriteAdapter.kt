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
import com.example.fatafatmangwao.R
import com.example.fatafatmangwao.databinding.UserFavItemBinding
import com.example.fatafatmangwao.model.wishlist.Data
import com.example.fatafatmangwao.utils.ClickListeners
import com.example.fatafatmangwao.utils.Extensions
import com.example.fatafatmangwao.utils.ListActionTypeClickListener

class UserFavouriteAdapter(private val listener: ClickListeners) :
    ListAdapter<Data, RecyclerView.ViewHolder>(DiffUtil()) {

    private var isFav = false
    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    inner class FavouriteViewHolder(private val binding: UserFavItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Data) {

            binding.apply {
                Glide.with(binding.root.context)
                    .load(Extensions.getImageUrl(item.images.first()))
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
                    .into(iivItem)
                tvItemPrice.text = item.price.toString()
                tvItemTitle.text = item.title
                ivFav.setImageResource(R.drawable.ic_stroke_heart)
                ivFav.setOnClickListener {
                    isFav = isFav.not()
                    listener.onItemClick(ListActionTypeClickListener.OnFavouriteClicked(ivFav, item.id, isFav))

                }
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