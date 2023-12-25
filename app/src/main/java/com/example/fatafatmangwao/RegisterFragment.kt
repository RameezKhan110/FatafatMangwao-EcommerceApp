package com.example.fatafatmangwao

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fatafatmangwao.Extensions.showSnackBar
import com.example.fatafatmangwao.Extensions.showToast
import com.example.fatafatmangwao.auth.Resource
import com.example.fatafatmangwao.auth.User
import com.example.fatafatmangwao.auth.viewmodel.AuthViewModel
import com.example.fatafatmangwao.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun observe(){
        binding.apply {
            authViewModel.registerUserObserver.observe(viewLifecycleOwner) {

                when (it) {
                    is Resource.Error -> {
                        progressBar.visibility = View.GONE
                        tvPasswordError.visibility = View.VISIBLE
                        tvPasswordError.text = it.message
                        Log.d("TAG", "error in registration: ${it.message}")
                    }

                    is Resource.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        requireContext().showToast("Please wait", Toast.LENGTH_SHORT)
                    }

                    is Resource.Success -> {
                        progressBar.visibility = View.GONE
                        findNavController().navigate(R.id.action_registerFragment_to_verifyOtpFragment)
                        rootLayout.showSnackBar(
                            "Welcome $",
                            Snackbar.LENGTH_INDEFINITE
                        )
                        Log.d("TAG", "data: " + it.data?.token)
                        Log.d("TAG", "data: " + it.data?.error)
                        it.data?.token?.let { token ->
                            Extensions.storeUserToken(requireContext(), token)
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()

        val bottomNav =
            requireActivity().findViewById<ExpandableBottomBar>(R.id.expandableBottomBar)
        bottomNav.visibility = View.GONE

        binding.goToLoginTv.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.signupBtn.setOnClickListener {
            binding.apply {
                val userName = etUsername.text.toString().trim()
                val userEmail = etEmail.text.toString().trim()
                val userPassword = etPassword.text.toString().trim()
                val userConfirmPassword = etConfirmPassword.text.toString().trim()

                if (userPassword == userConfirmPassword) {
                    authViewModel.registerUser(User(userName, userEmail, userPassword))
                } else {
                    tvPasswordError.visibility = View.VISIBLE
                    tvPasswordError.text = "Password doesn't match"
                }
            }
        }
    }

}