package com.example.fatafatmangwao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fatafatmangwao.adapter.UserShopsAdapter
import com.example.fatafatmangwao.databinding.FragmentUserPopularShopsBinding
import com.example.fatafatmangwao.utils.ClickListeners
import com.example.fatafatmangwao.utils.ListActionTypeClickListener
import com.example.fatafatmangwao.utils.Resource
import com.example.fatafatmangwao.viewmodel.ActivityViewModel
import com.example.fatafatmangwao.viewmodel.SharedViewModel
import com.example.fatafatmangwao.viewmodel.ViewModelObservers

class UserPopularShopsFragment : Fragment(), ClickListeners {

    private lateinit var mBinding: FragmentUserPopularShopsBinding
    private val shopsAdapter = UserShopsAdapter(this@UserPopularShopsFragment)
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentUserPopularShopsBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setUpRecyclerView()
        observe()
        activityViewModel.getAllShops("", "653ab8d9eb53153e5b06038b")
    }

    private fun observe() {
        mBinding.apply {
            ViewModelObservers.getShopsObserver.observe(viewLifecycleOwner) {
                when(it) {
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
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
        when(clickListener) {
            is ListActionTypeClickListener.OnCategoryClicked -> {
                sharedViewModel.categoryId = clickListener.categoryId
                findNavController().navigate(R.id.action_userPopularShopsFragment_to_userSpecificShopFragment)
            }
        }
    }
}