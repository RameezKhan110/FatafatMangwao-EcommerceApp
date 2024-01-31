package com.example.fatafatmangwao

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fatafatmangwao.adapter.UserCartAdapter
import com.example.fatafatmangwao.databinding.CartConfirmOrderLayoutBinding
import com.example.fatafatmangwao.databinding.FragmentUserCartBinding
import com.example.fatafatmangwao.model.UpdateProductRequest
import com.example.fatafatmangwao.model.cart.Data
import com.example.fatafatmangwao.utils.ClickListeners
import com.example.fatafatmangwao.utils.Extensions
import com.example.fatafatmangwao.utils.Extensions.gone
import com.example.fatafatmangwao.utils.Extensions.showToast
import com.example.fatafatmangwao.utils.Extensions.visible
import com.example.fatafatmangwao.utils.ListActionTypeClickListener
import com.example.fatafatmangwao.utils.Resource
import com.example.fatafatmangwao.viewmodel.ActivityViewModel
import com.example.fatafatmangwao.viewmodel.SharedViewModel
import com.example.fatafatmangwao.viewmodel.ViewModelObservers
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar


class UserCartFragment : Fragment(), ClickListeners {

    private lateinit var mBinding: FragmentUserCartBinding
    private lateinit var itemLayoutBinding: CartConfirmOrderLayoutBinding
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val cartAdapter = UserCartAdapter(this@UserCartFragment)
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentUserCartBinding.inflate(inflater, container, false)
        val bottomNav =
            requireActivity().findViewById<ExpandableBottomBar>(R.id.expandableBottomBar)
        bottomNav.gone()
        itemLayoutBinding = CartConfirmOrderLayoutBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        observe()
        activityViewModel.getCart()

        mBinding.apply {

            tvAddMoreItems.setOnClickListener {
                findNavController().navigate(R.id.userSpecificShopFragment)
            }
            btnCashOnDelivery.setOnClickListener {
                findNavController().navigate(R.id.action_userCartFragment_to_confirmOrderFragment)
            }
        }


    }

    private fun observe() {
        mBinding.apply {

            ViewModelObservers.getCartObserver.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        clLoadingView.visibility = View.GONE
                        loadingView.cancelAnimation()
                        scrollView.visibility = View.VISIBLE

                    }

                    is Resource.Loading -> {
                        loadingView.playAnimation()
                        clLoadingView.visibility = View.VISIBLE
                        scrollView.visibility = View.GONE
                        clEmptyCart.gone()
                    }

                    is Resource.Success -> {
                        clLoadingView.visibility = View.GONE
                        loadingView.cancelAnimation()
                        scrollView.visibility = View.VISIBLE

                        Log.d("TAG", "data from get cart: ${it.data?.data?.cart}")
                        if (it.data?.data?.cart?.isEmpty() == true) {
                            scrollView.gone()
                            clEmptyCart.visible()
                        }
//                        scrollView.visible()
//                        emptyCartAnimation.gone()
//                        emptyCartAnimation.cancelAnimation()
                        it.data?.data.let { it1 ->
                            if (it1 != null) {
                                setData(it1)
                            }
                        }
                        cartAdapter.submitList(it.data?.data?.cart)
//                        sharedViewModel.productQuantity = it.data.data.cart.

//                        Extensions.clearLiveDataValue(ViewModelObservers._getCartObserver)
                    }
                }
            }

            ViewModelObservers.deleteFromCartObserver.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        Log.d("TAG", "item successfully deleted from cart")
                    }
                }
            }
        }
    }

    private fun setData(data: Data) {
        mBinding.apply {
            tvSubTotal.text = data.subTotal.toString()
            tvDc.text = data.delivery.toString()
            tvTotalAmount.text = data.total.toString()
        }
    }

    private fun setUpRecyclerView() {
        mBinding.rvList.layoutManager = NonScrollableLayoutManager(requireContext())
        mBinding.rvList.adapter = cartAdapter
    }

    override fun onItemClick(clickListener: ListActionTypeClickListener) {
        when (clickListener) {
            is ListActionTypeClickListener.OnCartDeleteClicked -> {
                activityViewModel.deleteFromCart(clickListener.productId)
                requireContext().showToast("Item deleted fom cart", Toast.LENGTH_SHORT)
            }

            is ListActionTypeClickListener.OnCartPlusOrMinusClicked -> {
                if (clickListener.method.equals("+")) {
                    val req = UpdateProductRequest("+")
                    activityViewModel.updateProductQuantity(clickListener.id, req)
                    ViewModelObservers.updateProductQuantityObserver.observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Error -> {

                            }

                            is Resource.Loading -> {

                            }

                            is Resource.Success -> {
                                it.data?.message?.let { it1 -> Log.d("TAG", it1) }

                            }
                        }
                    }
                } else {
                    val req = UpdateProductRequest("-")

                    activityViewModel.updateProductQuantity(clickListener.id, req)
                    ViewModelObservers.updateProductQuantityObserver.observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Error -> {

                            }

                            is Resource.Loading -> {

                            }

                            is Resource.Success -> {
                                it.data?.message?.let { it1 -> Log.d("TAG", it1) }
                            }
                        }
                    }
                }
            }

            else -> {

            }
        }
    }

    override fun onPause() {
        super.onPause()
        Extensions.apply {
            clearLiveDataValue(ViewModelObservers._getCartObserver)
            clearLiveDataValue(ViewModelObservers._deleteFromCartObserver)
            clearLiveDataValue(ViewModelObservers._updateProductQuantityObserver)
        }
    }
}