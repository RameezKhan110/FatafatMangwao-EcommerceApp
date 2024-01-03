package com.example.fatafatmangwao

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fatafatmangwao.activities.MainActivity
import com.example.fatafatmangwao.databinding.FragmentOnBoarding4Binding

class OnBoarding4Fragment : Fragment() {

    private lateinit var mBinding: FragmentOnBoarding4Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentOnBoarding4Binding.inflate(layoutInflater, container, false)

        mBinding.tvNext.setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
        return mBinding.root
    }

}