package com.example.fatafatmangwao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fatafatmangwao.adapter.UserShopsAdapter
import com.example.fatafatmangwao.databinding.FragmentUserPopularShopsBinding
import com.example.fatafatmangwao.model.shops.Data
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

class UserPopularShopsFragment : Fragment(), ClickListeners {

    private lateinit var mBinding: FragmentUserPopularShopsBinding
    private val shopsAdapter = UserShopsAdapter(this@UserPopularShopsFragment)
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val QRDataList = arrayListOf<Data>()
    private var fromQR: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentUserPopularShopsBinding.inflate(layoutInflater, container, false)

        val bottomNav =
            requireActivity().findViewById<ExpandableBottomBar>(R.id.expandableBottomBar)
        bottomNav.gone()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        fromQR = bundle?.getBoolean("fromQR") == true

        if (fromQR) {
            if(QRDataList.isEmpty()) {
                QRDataList.add(
                    Data(
                        shopname = "Imtiyaz Supermarket",
                        supermarketId = "Imtiyaaz001",
                        imageDrawable = R.drawable.imtiyaz
                    )
                )
                QRDataList.add(
                    Data(
                        shopname = "Dawood Supermarket",
                        supermarketId = "Dawood002",
                        imageDrawable = R.drawable.dawood
                    )
                )
                QRDataList.add(
                    Data(
                        shopname = "Highton Supermarket",
                        supermarketId = "Highton003",
                        imageDrawable = R.drawable.imtiyaz
                    )
                )
                QRDataList.add(
                    Data(
                        shopname = "Arwa Supermarket",
                        supermarketId = "Arwa004",
                        imageDrawable = R.drawable.arwa
                    )
                )
                QRDataList.add(
                    Data(
                        shopname = "Baig Mart",
                        supermarketId = "Baig005",
                        imageDrawable = R.drawable.baig
                    )
                )
            }
            shopsAdapter.submitList(QRDataList)
//            requireContext().showToast("You came from QR", Toast.LENGTH_SHORT)
        } else {
            sharedViewModel.categoryId?.let { activityViewModel.getAllShops("", it) }
        }
        setListeners()
        setUpRecyclerView()
        observe()

    }

    private fun observe() {
        mBinding.apply {
            ViewModelObservers.getShopsObserver.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        loadingView.visibility = View.GONE
                        loadingView.cancelAnimation()
                    }

                    is Resource.Loading -> {
                        loadingView.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        loadingView.visibility = View.GONE
                        loadingView.cancelAnimation()
                        shopsAdapter.submitList(it.data?.data)

                    }
                }
            }
        }
    }

    private fun setListeners() {
        mBinding.apply {
                backImg.setOnClickListener {
                    findNavController().popBackStack()

                }
        }
    }


    private fun setUpRecyclerView() {
        mBinding.apply {
            popularShopsRv.layoutManager = LinearLayoutManager(requireContext())
            popularShopsRv.adapter = shopsAdapter
        }
    }

    override fun onItemClick(clickListener: ListActionTypeClickListener) {
        when (clickListener) {
            is ListActionTypeClickListener.OnCategoryClicked -> {
                sharedViewModel.shopId = clickListener.categoryId
                findNavController().navigate(R.id.action_userPopularShopsFragment_to_userSpecificShopFragment)
            }

            is ListActionTypeClickListener.OnSupermarketClicked -> {
                sharedViewModel.supermarketId = clickListener.supermarketId
                findNavController().navigate(R.id.QRScannerFragment)
            }

            else -> {

            }
        }
    }

    override fun onPause() {
        super.onPause()
        Extensions.clearLiveDataValue(ViewModelObservers._getShopsObserver)
    }
}