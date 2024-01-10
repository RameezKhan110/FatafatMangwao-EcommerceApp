package com.example.fatafatmangwao

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fatafatmangwao.adapter.home.HomeListAdapter
import com.example.fatafatmangwao.databinding.FragmentHomeBinding
import com.example.fatafatmangwao.model.home.HomeData
import com.example.fatafatmangwao.model.home.HomeHorizontalRVModel
import com.example.fatafatmangwao.model.home.HomeVerticalRVModel
import com.example.fatafatmangwao.utils.ClickListeners
import com.example.fatafatmangwao.utils.ListActionTypeClickListener
import com.example.fatafatmangwao.utils.Resource
import com.example.fatafatmangwao.viewmodel.ActivityViewModel
import com.example.fatafatmangwao.viewmodel.ViewModelObservers
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar

class HomeFragment : Fragment(), ClickListeners {

    private lateinit var mBinding: FragmentHomeBinding
    private val homeAdapter = HomeListAdapter(this@HomeFragment)
    private val homeData = arrayListOf<HomeData>()
    private val activityViewModel: ActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        val bottomNav =
            requireActivity().findViewById<ExpandableBottomBar>(R.id.expandableBottomBar)
        bottomNav.visibility = View.VISIBLE
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerview()
        if (activityViewModel.homeData.isNullOrEmpty()) {
            observe()
        } else {
            homeAdapter.submitList(activityViewModel.homeData)
        }
        activityViewModel.getHomeDetails()
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
        TODO("Not yet implemented")
    }
}