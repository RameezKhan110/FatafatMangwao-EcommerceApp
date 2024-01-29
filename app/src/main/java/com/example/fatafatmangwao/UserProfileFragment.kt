package com.example.fatafatmangwao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fatafatmangwao.activities.MainActivity
import com.example.fatafatmangwao.databinding.FragmentUserProfileBinding
import com.example.fatafatmangwao.utils.Extensions
import com.example.fatafatmangwao.utils.Extensions.visible
import com.example.fatafatmangwao.viewmodel.ActivityViewModel
import com.example.fatafatmangwao.viewmodel.ViewModelObservers
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar

class UserProfileFragment : Fragment() {

    private lateinit var mBinding: FragmentUserProfileBinding
    private val viewModel: ActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentUserProfileBinding.inflate(layoutInflater, container, false)

        val bottomNav =
            requireActivity().findViewById<ExpandableBottomBar>(R.id.expandableBottomBar)
        bottomNav.visible()

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (requireActivity() as MainActivity).indicatorSwitcher(R.id.home)
            }

        })

        mBinding.apply {
            ivLogout.setOnClickListener {
                Extensions.clearLiveDataValue(ViewModelObservers._loginUserObserver)
                Extensions.clearUserToken(requireContext())

                findNavController().navigate(R.id.loginFragment)
            }
            tvEditProfile.setOnClickListener {
                findNavController().navigate(R.id.userEditProfile)
            }
        }
    }

}