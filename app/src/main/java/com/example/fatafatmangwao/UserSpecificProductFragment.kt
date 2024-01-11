package com.example.fatafatmangwao

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.fatafatmangwao.databinding.FragmentUserSpecificProductBinding
import com.example.fatafatmangwao.model.ProductRequest
import com.example.fatafatmangwao.model.specific_product.Product
import com.example.fatafatmangwao.utils.Resource
import com.example.fatafatmangwao.viewmodel.ActivityViewModel
import com.example.fatafatmangwao.viewmodel.SharedViewModel
import com.example.fatafatmangwao.viewmodel.ViewModelObservers

class UserSpecificProductFragment : Fragment() {

    private lateinit var mBinding: FragmentUserSpecificProductBinding
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var totalQuantityObserver: MutableLiveData<Int> = MutableLiveData()
    private var totalQuantity: Int = 0
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

        mBinding.apply {

            ivAdd.setOnClickListener {
                totalQuantity += 1
                tvQuantity.text = totalQuantity.toString()
                totalQuantityObserver.value = totalQuantityObserver.value?.plus(1)

            }

            ivMinus.setOnClickListener {
                if(totalQuantity > 0) {
                    totalQuantity -= 1
                    tvQuantity.text = totalQuantity.toString()
                    totalQuantityObserver.value = totalQuantityObserver.value?.minus(1)
                }



            }

            totalQuantityObserver.observe(viewLifecycleOwner) {
                if(totalQuantity > 0 ) {
                    viewYOurCartCd.visibility = View.VISIBLE
                } else {
                    viewYOurCartCd.visibility = View.GONE
                }
            }

            btnAddToCart.setOnClickListener {
                val request =
                    sharedViewModel.productId?.let { it1 -> ProductRequest(it1, totalQuantity.toString()) }
                if (request != null) {
                    activityViewModel.addToCart(request)
                }
            }

            goToCart.setOnClickListener {
                findNavController().navigate(R.id.action_userSpecificProductFragment_to_userCartFragment)
            }
        }
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
        mBinding.apply {

            ViewModelObservers.getSpecificProductObserver.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                    }

                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        it.data?.data?.let { it1 -> setListeners(it1.product) }
                    }
                }
            }

            ViewModelObservers.addToCartObserver.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        Log.d("TAG", "cart error ${it.message}")

                    }

                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        Log.d("TAG", "cart message: ${it.data?.message}")
                    }
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