package com.example.fatafatmangwao.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fatafatmangwao.R
import com.example.fatafatmangwao.utils.Extensions.showToast
import com.example.fatafatmangwao.utils.Resource
import com.example.fatafatmangwao.viewmodel.ActivityViewModel
import com.example.fatafatmangwao.databinding.FragmentLoginBinding
import com.example.fatafatmangwao.model.User
import com.example.fatafatmangwao.utils.Extensions
import com.example.fatafatmangwao.utils.Extensions.gone
import com.example.fatafatmangwao.utils.Extensions.visible
import com.example.fatafatmangwao.viewmodel.ViewModelObservers
import com.example.fatafatmangwao.viewmodel.ViewModelObservers.loginUserObserver
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar

class LoginFragment : Fragment() {

    private lateinit var mBinding: FragmentLoginBinding
    private val activityViewModel: ActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        if(Extensions.getUserToken(requireContext()).isNullOrEmpty().not()) {
            findNavController().navigate(R.id.splashFragment)
//            startActivity(Intent(requireActivity(), MapsActivity::class.java))

        }
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
                activityViewModel.loginUser(loginDetails)
            }
            signupBtn.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }
    private fun observe() {
        mBinding.apply {
            loginUserObserver.observe(viewLifecycleOwner) {
                when(it) {
                    is Resource.Error -> {
                        loadingView.gone()
                        it.message?.let { it1 -> requireContext().showToast(it1, Toast.LENGTH_SHORT) }
                    }
                    is Resource.Loading -> {
                        loadingView.visible()
                    }
                    is Resource.Success -> {
                        loadingView.gone()
                        it.data?.token?.let { it1 ->
                            Extensions.storeUserToken(requireContext(), it1)
                        }
                        requireContext().showToast("Logging In", Toast.LENGTH_SHORT)
//                        startActivity(Intent(requireActivity(), MapsActivity::class.java))
                        findNavController().navigate(R.id.splashFragment)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}