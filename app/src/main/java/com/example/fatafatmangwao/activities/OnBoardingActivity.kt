package com.example.fatafatmangwao.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fatafatmangwao.OnBoardingViewPagerAdapter
import com.example.fatafatmangwao.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.onBoardingViewPager.adapter = OnBoardingViewPagerAdapter(this)
        binding.dotsIndicator.attachTo(binding.onBoardingViewPager)
    }
}