package com.example.fatafatmangwao

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.fatafatmangwao.databinding.FragmentUserSpecificProductBinding
import com.example.fatafatmangwao.model.specific_product.Product
import com.example.fatafatmangwao.utils.Resource
import com.example.fatafatmangwao.viewmodel.ActivityViewModel
import com.example.fatafatmangwao.viewmodel.SharedViewModel
import com.example.fatafatmangwao.viewmodel.ViewModelObservers

class  UserSpecificProductFragment : Fragment() {

    private lateinit var mBinding: FragmentUserSpecificProductBinding
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
//    private val userSpecificProductAdapter = UserSpecificProductAdapter(this@UserSpecificProductFragment)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentUserSpecificProductBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setUpRecyclerView()
        observe()
        sharedViewModel.productId?.let { activityViewModel.getSpecificProduct(it) }
    }

    private fun setListeners(productDetails: Product) {
        mBinding.apply {
            Glide.with(requireContext()).load(productDetails.images.first()).into(ivProduct)
            tvProductName.text = productDetails.title
            tvProductPrice.text = productDetails.price.toString()
            tvRating.text = productDetails.ratingCount.toString()
            tvDesc.text = productDetails.description
        }
    }

    private fun observe() {
        ViewModelObservers.getSpecificProductObserver.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    it.data?.data?.let { it1 -> setListeners(it1.product) }
//                    userSpecificProductAdapter.submitList(it.data.data)
                }
            }
        }
    }

//    private fun setUpRecyclerView() {
//        mBinding.apply {
//            rvRealtedItems.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            rvRealtedItems.adapter = userSpecificProductAdapter
//        }
//    }

}