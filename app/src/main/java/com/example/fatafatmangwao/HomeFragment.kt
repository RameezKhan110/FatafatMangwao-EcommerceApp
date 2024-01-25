package com.example.fatafatmangwao

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fatafatmangwao.activities.MainActivity
import com.example.fatafatmangwao.adapter.home.HomeCategoryAndPopularShopAdapter
import com.example.fatafatmangwao.adapter.home.HomeListAdapter
import com.example.fatafatmangwao.databinding.FragmentHomeBinding
import com.example.fatafatmangwao.model.home.HomeData
import com.example.fatafatmangwao.model.home.HomeHorizontalRVModel
import com.example.fatafatmangwao.model.home.HomeVerticalRVModel
import com.example.fatafatmangwao.utils.ClickListeners
import com.example.fatafatmangwao.utils.Extensions
import com.example.fatafatmangwao.utils.Extensions.gone
import com.example.fatafatmangwao.utils.Extensions.visible
import com.example.fatafatmangwao.utils.ListActionTypeClickListener
import com.example.fatafatmangwao.utils.Resource
import com.example.fatafatmangwao.viewmodel.ActivityViewModel
import com.example.fatafatmangwao.viewmodel.ViewModelObservers
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import kotlinx.coroutines.MainCoroutineDispatcher

class HomeFragment : Fragment(), ClickListeners {

    private lateinit var mBinding: FragmentHomeBinding
    private val homeAdapter = HomeListAdapter(this@HomeFragment)
    private val homeData = arrayListOf<HomeData>()
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val mainActivity = MainActivity()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        val bottomNav =
            requireActivity().findViewById<ExpandableBottomBar>(R.id.expandableBottomBar)
        bottomNav.visible()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }

        })
        setUpRecyclerview()
        if (activityViewModel.homeData.isNullOrEmpty()) {
            observe()
        } else {
            homeAdapter.submitList(activityViewModel.homeData)
        }
        activityViewModel.getHomeDetails()
        mBinding.ivLogout.setOnClickListener {
            Extensions.clearLiveDataValue(ViewModelObservers._loginUserObserver)
            Extensions.clearUserToken(requireContext())
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

    private fun observe() {
        ViewModelObservers.getHomeDetailsObserver.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }

                is Resource.Success -> {

                    homeData.add(
                        HomeData.homeHorizontalItems(
                            HomeHorizontalRVModel.BannerModelItem(
                                it.data?.data?.banners ?: arrayListOf()
                            )
                        )
                    )
                    homeData.add(
                        HomeData.homeVerticalItems(
                            HomeVerticalRVModel.ItemHeadingModel(
                                "Categories",
                                "View All"
                            )
                        )
                    )
                    homeData.add(
                        HomeData.homeVerticalItems(
                            HomeVerticalRVModel.CategoryModelItem(
                                it.data?.data?.categories ?: arrayListOf()
                            )
                        )
                    )

                    homeData.add(
                        HomeData.homeVerticalItems(
                            HomeVerticalRVModel.SupermarketItemModel(
                                "Testing"
                            )
                        )
                    )
                    homeData.add(
                        HomeData.homeVerticalItems(
                            HomeVerticalRVModel.ItemHeadingModel(
                                "Popular Shops",
                                "View All"
                            )
                        )
                    )
                    homeData.add(
                        HomeData.homeVerticalItems(
                            HomeVerticalRVModel.PopularShopItem(
                                it.data?.data?.popularShops ?: arrayListOf()
                            )
                        )
                    )
                    activityViewModel.homeData = homeData
                    homeAdapter.submitList(activityViewModel.homeData)
                }
            }
        }
    }

    private fun setUpRecyclerview() {
        mBinding.apply {
            rvList.layoutManager = LinearLayoutManager(requireContext())
            rvList.adapter = homeAdapter
        }
    }

    override fun onItemClick(clickListener: ListActionTypeClickListener) {
        when(clickListener) {
            is ListActionTypeClickListener.OnHeadingClicked -> {
                if(clickListener.heading == "Categories") {
                    findNavController().navigate(R.id.action_homeFragment_to_userCategoryFragment)
                } else {
                    findNavController().navigate(R.id.action_homeFragment_to_userPopularShopsFragment)
                }
            }
            else -> {

            }
        }
    }
}