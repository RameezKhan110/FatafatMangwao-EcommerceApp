package com.example.fatafatmangwao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fatafatmangwao.Extensions.showSnackBar
import com.example.fatafatmangwao.Extensions.showToast
import com.example.fatafatmangwao.auth.Resource
import com.example.fatafatmangwao.auth.User
import com.example.fatafatmangwao.auth.viewmodel.AuthViewModel
import com.example.fatafatmangwao.databinding.FragmentLoginBinding
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar

class LoginFragment : Fragment() {

    private lateinit var mBinding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        val bottomNav = requireActivity().findViewById<ExpandableBottomBar>(R.id.expandableBottomBar)
        bottomNav.visibility = View.GONE

        mBinding.apply {
            signInBtn.setOnClickListener {
                val userEmail = etEmail.text.toString().trim()
                val userPassword = etPassword.text.toString().trim()
                val loginDetails = User(email= userEmail, password= userPassword)
                authViewModel.loginUser(loginDetails)

            }
        }
    }
    private fun observe() {
        mBinding.apply {
            authViewModel.loginUserObserver.observe(viewLifecycleOwner) {
                when(it) {
                    is Resource.Error -> {
                        progressBar.visibility = View.GONE
                        it.message?.let { it1 -> requireContext().showToast(it1, Toast.LENGTH_SHORT) }
                    }
                    is Resource.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        progressBar.visibility = View.GONE
                        it.data?.token?.let { it1 ->
                            Extensions.storeUserToken(requireContext(), it1)
                        }
                        requireContext().showToast("Logging In", Toast.LENGTH_SHORT)
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        requireActivity().finish()
                    }
                }
            }
        }
    }
}