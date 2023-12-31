package com.example.fatafatmangwao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fatafatmangwao.adapter.ClickListeners
import com.example.fatafatmangwao.adapter.ListActionClickListener
import com.example.fatafatmangwao.adapter.UserCategoryAdapter
import com.example.fatafatmangwao.databinding.FragmentUserCategoryBinding
import com.example.fatafatmangwao.utils.Extensions.showToast
import com.example.fatafatmangwao.utils.Resource
import com.example.fatafatmangwao.viewmodel.ActivityViewModel
import com.example.fatafatmangwao.viewmodel.ViewModelObservers.getCategoryObserver
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar

class UserCategoryFragment : Fragment(), ListActionClickListener {

    private lateinit var mBinding: FragmentUserCategoryBinding
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val userCategoryAdapter = UserCategoryAdapter(this@UserCategoryFragment)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentUserCategoryBinding.inflate(layoutInflater, container, false)

        val bottomNav = requireActivity().findViewById<ExpandableBottomBar>(R.id.expandableBottomBar)
        bottomNav.visibility = View.VISIBLE
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
        setRecyclerView()
        observe()
        activityViewModel.getCategories(2)
    }

    private fun observe() {
        mBinding.apply {
            getCategoryObserver.observe(viewLifecycleOwner) {
                when(it) {
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        userCategoryAdapter.submitList(it.data?.data)
                    }
                }
            }
        }
    }

    private fun setRecyclerView() {
        mBinding.apply {
            rvList.layoutManager = GridLayoutManager(requireContext(), 3)
            rvList.adapter = userCategoryAdapter
        }
    }

    private fun setListener() {
        mBinding.apply {

        }
    }
    override fun onItemClick(clickListener: ClickListeners) {
        when(clickListener) {
            is ClickListeners.OnCategoryClicked -> {
                requireContext().showToast("Item clicked", Toast.LENGTH_SHORT)
                findNavController().navigate(R.id.action_userCategoryFragment_to_userPopularShopsFragment)
            }
        }

    }
}