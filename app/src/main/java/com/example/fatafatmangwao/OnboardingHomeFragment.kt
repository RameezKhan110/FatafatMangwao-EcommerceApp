package com.example.fatafatmangwao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fatafatmangwao.databinding.FragmentOnboardingHomeBinding

class OnboardingHomeFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingHomeBinding.inflate(layoutInflater, container, false)

        binding.onBoardingViewPager.adapter = OnBoardingViewPagerAdapter(requireActivity())
        binding.dotsIndicator.attachTo(binding.onBoardingViewPager)

        return binding.root
    }

}