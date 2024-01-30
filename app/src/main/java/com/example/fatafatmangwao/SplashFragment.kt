package com.example.fatafatmangwao

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fatafatmangwao.databinding.FragmentSplashBinding
import com.example.fatafatmangwao.utils.Extensions.gone
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private lateinit var mBinding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSplashBinding.inflate(layoutInflater, container, false)

        val bottomNav =
            requireActivity().findViewById<ExpandableBottomBar>(R.id.expandableBottomBar)
        bottomNav.gone()

        val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_anim)
        val fadeOutAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out_anim)

        mBinding.ivSplash.startAnimation(fadeInAnimation)

        Handler().postDelayed({
            mBinding.ivSplash.startAnimation(fadeOutAnimation)

            Handler().postDelayed({
                findNavController().navigate(R.id.homeFragment)
            }, fadeOutAnimation.duration)
        }, fadeInAnimation.duration)

        return mBinding.root
    }

}