package com.example.fatafatmangwao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fatafatmangwao.databinding.FragmentCardCredentialsBinding

class CardCredentialsFragment : Fragment() {

    private lateinit var mBinding: FragmentCardCredentialsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCardCredentialsBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }
}