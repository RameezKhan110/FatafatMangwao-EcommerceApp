package com.example.fatafatmangwao

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.fatafatmangwao.adapter.UserSpecificShopAdapter
import com.example.fatafatmangwao.databinding.FragmentUserSpecificShopBinding
import com.example.fatafatmangwao.model.specific_shops.Shop
import com.example.fatafatmangwao.utils.ClickListeners
import com.example.fatafatmangwao.utils.Extensions
import com.example.fatafatmangwao.utils.Extensions.autoDisableSnackBar
import com.example.fatafatmangwao.utils.Extensions.gone
import com.example.fatafatmangwao.utils.Extensions.visible
import com.example.fatafatmangwao.utils.ListActionTypeClickListener
import com.example.fatafatmangwao.utils.Resource
import com.example.fatafatmangwao.viewmodel.ActivityViewModel
import com.example.fatafatmangwao.viewmodel.SharedViewModel
import com.example.fatafatmangwao.viewmodel.ViewModelObservers
import com.google.android.material.snackbar.Snackbar
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar

class UserSpecificShopFragment : Fragment(), ClickListeners {

    private lateinit var mBinding: FragmentUserSpecificShopBinding
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val userSpecificShopAdapter = UserSpecificShopAdapter(this@UserSpecificShopFragment)
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentUserSpecificShopBinding.inflate(layoutInflater, container, false)
        val bottomNav =
            requireActivity().findViewById<ExpandableBottomBar>(R.id.expandableBottomBar)
        bottomNav.gone()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setUpRecyclerView()
        observe()
        sharedViewModel.shopId?.let { activityViewModel.getSpecificShop(it) }

        mBinding.apply {
            backImg.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setUpRecyclerView() {
        mBinding.apply {
            rvList.layoutManager = GridLayoutManager(requireContext(), 2)
            rvList.adapter = userSpecificShopAdapter
        }
    }

    private fun setListeners() {
        mBinding.apply {

        }
    }

    private fun observe() {
        mBinding.apply {
            ViewModelObservers.getSpecificShopObserver.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        loadingView.visibility = View.GONE
                        specificShopLayout.root.visibility = View.VISIBLE
                        loadingView.cancelAnimation()
                    }

                    is Resource.Loading -> {
                        loadingView.visibility = View.VISIBLE
                        loadingView.playAnimation()
                        specificShopLayout.root.visibility = View.GONE
                    }

                    is Resource.Success -> {
                        loadingView.visibility = View.GONE
                        loadingView.cancelAnimation()
                        specificShopLayout.root.visibility = View.VISIBLE
                        it.data?.data?.let { it1 -> setLayoutData(it1.shop) }
                        userSpecificShopAdapter.submitList(it.data?.data?.updateProducts)
                    }
                }
            }
        }
    }

    private fun setLayoutData(shopData: Shop) {
        mBinding.specificShopLayout.apply {
            shopNameTv.text = shopData.shopname
            minimumPriceTv.text = "Minimum Rs.300"
            Glide.with(requireContext()).load(Extensions.getImageUrl(shopData.profile))
                .into(shopImg)
            deliveryChargesTv.text = "Free"
            tvDeliveryTime.text = "Deliver in 20 min"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(clickListener: ListActionTypeClickListener) {
        when (clickListener) {
            is ListActionTypeClickListener.OnFavouriteClicked -> {
                clickListener.apply {
                    activityViewModel.addToFavourite(clickListener.productId)
                    ViewModelObservers.addToFavouriteObserver.observe(viewLifecycleOwner) {
                        when(it) {
                            is Resource.Error -> {

                            }
                            is Resource.Loading -> {

                            }
                            is Resource.Success -> {
                                it.message?.let { it1 -> mBinding.root.autoDisableSnackBar(it1, Snackbar.LENGTH_LONG) }
                                Log.d("TAG", "on fav: ${it.data?.message}")
                                sharedViewModel.shopId?.let { it1 ->
                                    activityViewModel.getSpecificShop(
                                        it1
                                    )
                                }
                                userSpecificShopAdapter.notifyDataSetChanged()
                            }
                        }
                    }

                }
            }

            is ListActionTypeClickListener.OnProductClicked -> {
                sharedViewModel.productId = clickListener.productId
                findNavController().navigate(R.id.action_userSpecificShopFragment_to_userSpecificProductFragment)
            }

            else -> {

            }
        }
    }

    override fun onPause() {
        super.onPause()
        Extensions.clearLiveDataValue(ViewModelObservers._addToFavouriteObserver)
        Extensions.clearLiveDataValue(ViewModelObservers._getSpecificShopObserver)
    }
}