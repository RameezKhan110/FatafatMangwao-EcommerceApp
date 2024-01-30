package com.example.fatafatmangwao.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fatafatmangwao.OtpData
import com.example.fatafatmangwao.R
import com.example.fatafatmangwao.utils.Extensions.autoDisableSnackBar
import com.example.fatafatmangwao.utils.Extensions.showToast
import com.example.fatafatmangwao.utils.Resource
import com.example.fatafatmangwao.viewmodel.ActivityViewModel
import com.example.fatafatmangwao.databinding.FragmentVerifyOtpBinding
import com.example.fatafatmangwao.utils.Extensions
import com.example.fatafatmangwao.viewmodel.ViewModelObservers.resendOtpObserver
import com.example.fatafatmangwao.viewmodel.ViewModelObservers.verifyOtpObserver
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class VerifyOtpFragment : Fragment() {

    private lateinit var mBinding: FragmentVerifyOtpBinding
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private var otp: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentVerifyOtpBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        mBinding.apply {
            otpView.setOtpCompletionListener { userOtp ->
                otp = userOtp
            }
            btnVerifyOtp.setOnClickListener {
                Log.d("TAG", "Auth Token: ${Extensions.getUserToken(requireContext())}")

                val otpRequestBody = otp?.toRequestBody("text/plain".toMediaTypeOrNull())
                val otpData = otp?.let { it1 -> OtpData(it1) }
                if (otpRequestBody != null) {
                    if (otpData != null) {
                        activityViewModel.verifyOtp(otpData)
                    }
                }
            }

            ivBack.setOnClickListener {
                findNavController().navigate(R.id.action_verifyOtpFragment_to_registerFragment)
            }

            tvResendOTP.setOnClickListener {
                activityViewModel.resendOtp()
            }
        }

    }

    private fun observe() {
        mBinding.apply {
            verifyOtpObserver.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        progressBar.visibility = View.GONE
                        tvOtpError.visibility = View.VISIBLE
                        tvOtpError.text = it.message
                        Log.d("TAG", "error on verifying otp: ${it.message}")
                    }

                    is Resource.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        progressBar.visibility = View.GONE
                        findNavController().navigate(R.id.splashFragment)
                        Log.d("TAG", "token on verify: ${it.data?.token}")
                        it.data?.token?.let { token ->
                            Extensions.storeUserToken(requireContext(), token)
                        }
                    }
                }
            }

            resendOtpObserver.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        progressBar.visibility = View.GONE
                        requireActivity().showToast("Internet not connected", Toast.LENGTH_SHORT)
                    }
                    is Resource.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        progressBar.visibility = View.GONE
                        root.autoDisableSnackBar("Otp has been send it your email", Snackbar.LENGTH_LONG)
                    }
                }
            }
        }
    }
}