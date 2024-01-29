package com.example.fatafatmangwao.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fatafatmangwao.databinding.CartConfirmOrderLayoutBinding
import com.example.fatafatmangwao.model.cart.Cart
import com.example.fatafatmangwao.model.cart.Data
import com.example.fatafatmangwao.utils.ClickListeners
import com.example.fatafatmangwao.utils.Extensions
import com.example.fatafatmangwao.utils.ListActionTypeClickListener
import kotlin.math.min

class UserCartAdapter(val listener: ClickListeners) :
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
            binding.apply {
                binding.tvItemName
                Glide.with(binding.root.context).load(item.images?.let { Extensions.getImageUrl(it.first()) })
                    .into(ivItemImage)
                binding.tvItemName.text = item.title
                binding.tvItemQuantity.text = item.quantity.toString()
                binding.tvItemPrice.text = item.price.toString()
                tvChangeableQuantity.text = item.quantity.toString()

                ivDelete.setOnClickListener {
                    item._id?.let { it1 ->
                        ListActionTypeClickListener.OnCartDeleteClicked(
                            it1
                        )
                    }?.let { it2 -> listener.onItemClick(it2) }
                }

                btnPlus.setOnClickListener {
                    item._id?.let { it1 ->
                        ListActionTypeClickListener.OnCartPlusOrMinusClicked(
                            it1,
                            "+"
                        )
                    }?.let { it2 ->
                        listener.onItemClick(
                            it2
                        )
                    }
                }
                btnMinus.setOnClickListener {
                    item._id?.let { it1 ->
                        ListActionTypeClickListener.OnCartPlusOrMinusClicked(
                            it1,
                            "-"
                        )
                    }?.let { it2 ->
                        listener.onItemClick(
                            it2
                        )
                    }
                }
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