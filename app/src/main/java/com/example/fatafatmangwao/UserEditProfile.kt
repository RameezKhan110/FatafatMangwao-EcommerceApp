package com.example.fatafatmangwao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fatafatmangwao.databinding.FragmentUserEditProfileBinding
import com.example.fatafatmangwao.model.User
import com.example.fatafatmangwao.utils.Extensions
import com.example.fatafatmangwao.utils.Extensions.gone
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar

class UserEditProfile : Fragment() {

    private lateinit var mBinding: FragmentUserEditProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentUserEditProfileBinding.inflate(layoutInflater, container, false)

        val bottomNav =
            requireActivity().findViewById<ExpandableBottomBar>(R.id.expandableBottomBar)
        bottomNav.gone()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = Extensions.getUserDetails(requireContext())

        mBinding.apply {
            if (user != null) {
                etUsername.setText(user.name)
                etEmail.setText(user.email)
                etPhone.hint = "0342-3652910"
            }
        }

    }

}