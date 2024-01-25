package com.example.fatafatmangwao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fatafatmangwao.activities.MainActivity
import com.example.fatafatmangwao.utils.ListActionTypeClickListener
import com.example.fatafatmangwao.utils.ClickListeners
import com.example.fatafatmangwao.adapter.UserCategoryAdapter
import com.example.fatafatmangwao.databinding.FragmentUserCategoryBinding
import com.example.fatafatmangwao.utils.Extensions
import com.example.fatafatmangwao.utils.Extensions.showToast
import com.example.fatafatmangwao.utils.Extensions.visible
import com.example.fatafatmangwao.utils.Resource
import com.example.fatafatmangwao.viewmodel.ActivityViewModel
import com.example.fatafatmangwao.viewmodel.SharedViewModel
import com.example.fatafatmangwao.viewmodel.ViewModelObservers
import com.example.fatafatmangwao.viewmodel.ViewModelObservers.getCategoryObserver
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserCategoryFragment : Fragment(), ClickListeners {

    private lateinit var mBinding: FragmentUserCategoryBinding
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val userCategoryAdapter = UserCategoryAdapter(this@UserCategoryFragment)
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentUserCategoryBinding.inflate(layoutInflater, container, false)

        val bottomNav =
            requireActivity().findViewById<ExpandableBottomBar>(R.id.expandableBottomBar)
        bottomNav.visible()
//        (requireActivity() as MainActivity).indicatorSwitcher(R.id.categories)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (requireActivity() as MainActivity).indicatorSwitcher(R.id.home)
            }

        })
        setListener()
        setRecyclerView()
        observe()
        activityViewModel.getCategories(2)

        mBinding.cartImg.setOnClickListener {
            findNavController().navigate(R.id.action_userCategoryFragment_to_userCartFragment)
        }
    }

    private fun observe() {
        mBinding.apply {
            getCategoryObserver.observe(viewLifecycleOwner) {
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

    override fun onItemClick(clickListener: ListActionTypeClickListener) {
        when (clickListener) {
            is ListActionTypeClickListener.OnCategoryClicked -> {
                sharedViewModel.categoryId = clickListener.categoryId
                requireContext().showToast("Item clicked", Toast.LENGTH_SHORT)
                findNavController().navigate(R.id.action_userCategoryFragment_to_userPopularShopsFragment)
            }

            else -> {

            }
        }

    }

    override fun onPause() {
        super.onPause()
        Extensions.clearLiveDataValue(ViewModelObservers._getCategoryObserver)
    }

}