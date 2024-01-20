package com.example.fatafatmangwao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fatafatmangwao.databinding.FragmentUserProfileBinding
import com.example.fatafatmangwao.utils.Extensions
import com.example.fatafatmangwao.viewmodel.ActivityViewModel
import com.example.fatafatmangwao.viewmodel.ViewModelObservers

class UserProfileFragment : Fragment() {

    private lateinit var mBinding: FragmentUserProfileBinding
    private val viewModel: ActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentUserProfileBinding.inflate(layoutInflater, container, false)

        mBinding.apply {
            ivLogout.setOnClickListener {
                Extensions.clearLiveDataValue(ViewModelObservers._loginUserObserver)
                Extensions.clearUserToken(requireContext())
                findNavController().navigate(R.id.action_userProfileFragment_to_loginFragment)
            }
        }
        return mBinding.root
    }

}