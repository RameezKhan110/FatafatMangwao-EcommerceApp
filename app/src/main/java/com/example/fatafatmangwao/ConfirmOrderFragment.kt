package com.example.fatafatmangwao

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fatafatmangwao.adapter.ConfirmOrderAdapter
import com.example.fatafatmangwao.databinding.FragmentConfirmOrderBinding
import com.example.fatafatmangwao.model.cart.Data
import com.example.fatafatmangwao.utils.Extensions.autoDisableSnackBar
import com.example.fatafatmangwao.utils.Extensions.showSnackBar
import com.example.fatafatmangwao.utils.Resource
import com.example.fatafatmangwao.viewmodel.ActivityViewModel
import com.example.fatafatmangwao.viewmodel.ViewModelObservers
import com.google.android.material.snackbar.Snackbar

class ConfirmOrderFragment : Fragment() {

    private lateinit var mBinding: FragmentConfirmOrderBinding
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val confirmOrderAdapter = ConfirmOrderAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentConfirmOrderBinding.inflate(layoutInflater, container, false)


        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observe()
        activityViewModel.getCart()
        activityViewModel.placeOrder()
        mBinding.btnPlaceOrder.setOnClickListener {
            findNavController().navigate(R.id.action_confirmOrderFragment_to_thanksForPurchaseFragment)
        }
    }

    private fun observe() {
        mBinding.apply {

            ViewModelObservers.getCartObserver.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        loadingView.visibility = View.GONE
                        loadingView.cancelAnimation()
                        scrollView.visibility = View.VISIBLE
                    }

                    is Resource.Loading -> {
                        loadingView.visibility = View.VISIBLE
                        scrollView.visibility = View.GONE
                    }

                    is Resource.Success -> {
                        loadingView.visibility = View.GONE
                        loadingView.cancelAnimation()
                        scrollView.visibility = View.VISIBLE
                        Log.d("TAG", "data from get cart: ${it.data?.data?.cart}")
                        it?.data?.data?.let { it1 -> setData(it1) }
                        confirmOrderAdapter.submitList(it.data?.data?.cart)
                    }
                }
            }

            ViewModelObservers.placeOrderObserver.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        loadingView.visibility = View.GONE
                        loadingView.cancelAnimation()
                        scrollView.visibility = View.VISIBLE

                    }

                    is Resource.Loading -> {
                        loadingView.visibility = View.VISIBLE
                        scrollView.visibility = View.GONE
                    }

                    is Resource.Success -> {
                        loadingView.visibility = View.GONE
                        loadingView.cancelAnimation()
                        scrollView.visibility = View.VISIBLE
                        mBinding.root.autoDisableSnackBar(
                            "Your order has been placed successfully",
                            Snackbar.LENGTH_LONG
                        )
                        findNavController().navigate(R.id.action_confirmOrderFragment_to_thanksForPurchaseFragment)
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
        mBinding.rvList.adapter = confirmOrderAdapter
    }
}