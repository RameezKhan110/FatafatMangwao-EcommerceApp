package com.example.fatafatmangwao.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fatafatmangwao.databinding.CartConfirmOrderLayoutBinding
import com.example.fatafatmangwao.model.cart.Cart
import com.example.fatafatmangwao.utils.Extensions
import com.example.fatafatmangwao.utils.Extensions.gone

class ConfirmOrderAdapter :
    androidx.recyclerview.widget.ListAdapter<Cart, RecyclerView.ViewHolder>(DiffUtil()) {

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Cart>() {
        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem == newItem
        }
    }

    inner class CartViewHolder(private val binding: CartConfirmOrderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Cart) {
            binding.apply{
                binding.tvItemName
                Glide.with(binding.root.context).load(Extensions.getImageUrl(item.images.first())).into(ivItemImage)
                binding.tvItemName.text = item.title
                binding.tvItemQuantity.text = item.quantity.toString()
                binding.tvItemPrice.text = item.price.toString()
                grpDeleteAndQuantities.gone()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CartViewHolder(
            CartConfirmOrderLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as CartViewHolder).bind(item)
    }
}