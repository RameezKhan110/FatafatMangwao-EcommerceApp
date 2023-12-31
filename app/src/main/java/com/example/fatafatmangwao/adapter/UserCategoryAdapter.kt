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
import com.example.fatafatmangwao.model.category.Data
import com.example.fatafatmangwao.utils.Extensions

class UserCategoryAdapter(private val listener: ListActionClickListener) :
    ListAdapter<Data, RecyclerView.ViewHolder>(DiffUtil()) {

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    inner class CategoryViewHolder(private val binding: UserCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Data) {
            binding.apply {

//            val glide = Glide.with(ivItem)
//            val requestOptions = RequestOptions().dontTransform()
//            if (errorHolder != null)
//                glide.applyDefaultRequestOptions(
//                    requestOptions.placeholder(R.drawable.food)
//                        .error(errorHolder)
//                )
//            glide.load(item.image).into(ivItem)
                Glide.with(binding.root.context)
                    .load(Extensions.getImageUrl(item.image))
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
                    .into(ivItem)
                tvItemName.text = item.name
                ivItem.setOnClickListener {
                    listener?.onItemClick(ClickListeners.OnCategoryClicked(item.id))
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            UserCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as CategoryViewHolder).bind(item)
    }
}