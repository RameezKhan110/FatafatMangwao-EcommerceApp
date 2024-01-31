package com.example.fatafatmangwao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fatafatmangwao.activities.MainActivity
import com.example.fatafatmangwao.databinding.FragmentThanksForPurchaseBinding
import com.example.fatafatmangwao.utils.Extensions.autoDisableSnackBar
import com.example.fatafatmangwao.utils.Extensions.gone
import com.example.fatafatmangwao.viewmodel.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar

class ThanksForPurchaseFragment : Fragment() {

    private lateinit var mBinding: FragmentThanksForPurchaseBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentThanksForPurchaseBinding.inflate(layoutInflater, container, false)
        val bottomNav =
            requireActivity().findViewById<ExpandableBottomBar>(R.id.expandableBottomBar)
        bottomNav.gone()

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.supermarketCartItems.clear()
        mBinding.root.autoDisableSnackBar(
            "Your order has been placed successfully",
            Snackbar.LENGTH_LONG
        )
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.userCategoryFragment, false)
        }

        mBinding.backToHomeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_thanksForPurchaseFragment_to_homeFragment)
        }

    }
}