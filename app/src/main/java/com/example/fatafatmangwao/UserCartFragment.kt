package com.example.fatafatmangwao

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.fatafatmangwao.databinding.CartConfirmOrderLayoutBinding
import com.example.fatafatmangwao.databinding.FragmentUserCartBinding
import com.example.fatafatmangwao.model.cart.Data
import com.example.fatafatmangwao.utils.Resource
import com.example.fatafatmangwao.viewmodel.ActivityViewModel
import com.example.fatafatmangwao.viewmodel.ViewModelObservers


class UserCartFragment : Fragment() {

    private lateinit var mBinding: FragmentUserCartBinding
    private lateinit var itemLayoutBinding: CartConfirmOrderLayoutBinding
    private val activityViewModel: ActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentUserCartBinding.inflate(inflater, container, false)
        itemLayoutBinding = CartConfirmOrderLayoutBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        activityViewModel.getCart()

    }

    private fun observe() {
        ViewModelObservers.getCartObserver.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    Log.d("TAG", "data from get cart: $it" )
//                    it.data?.data?.let { it1 ->
//                        val itemData = setData(it1)
//                        var totalComponents = 4
//                        itemData.forEach { view ->
//                            mBinding.root.children.forEach {
//                                if (it is ViewGroup) {
//                                    mBinding.cl1.addView(view.root, totalComponents)
//                                    totalComponents++
//                                }
//                            }
//                        }
//                    }
                }
            }
        }
    }

    private fun setData(data: Data): List<CartConfirmOrderLayoutBinding> {
//        val layout2 = LayoutInflater.from(requireContext()).inflate(
//            R.layout.cart_confirm_order_layout,
//            mBinding.cl1,
//            false
//        )
        val list = arrayListOf<CartConfirmOrderLayoutBinding>()
        data.cart.forEach {
            val itemView = itemLayoutBinding.apply {
                Glide.with(requireContext()).load(it.images.first()).into(ivItemImage)
                tvItemName.text = it.title
                tvItemQuantity.text = it.quantity.toString()
                tvItemPrice.text = it.price.toString()
            }
            list.add(itemView)
        }
        return list
    }
}