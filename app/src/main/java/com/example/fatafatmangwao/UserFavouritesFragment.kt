package com.example.fatafatmangwao

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fatafatmangwao.adapter.UserFavouriteAdapter
import com.example.fatafatmangwao.databinding.ActivityMainBinding
import com.example.fatafatmangwao.databinding.FragmentUserFavouritesBinding
import com.example.fatafatmangwao.utils.ClickListeners
import com.example.fatafatmangwao.utils.ListActionTypeClickListener
import com.example.fatafatmangwao.utils.Resource
import com.example.fatafatmangwao.viewmodel.ActivityViewModel
import com.example.fatafatmangwao.viewmodel.ViewModelObservers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserFavouritesFragment : Fragment(), ClickListeners {

    private lateinit var mBindiing: FragmentUserFavouritesBinding
    private val userFavouriteAdapter = UserFavouriteAdapter(this@UserFavouritesFragment)
    private val activityViewModel: ActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBindiing = FragmentUserFavouritesBinding.inflate(layoutInflater, container, false)
        return mBindiing.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerview()
        observe()
        activityViewModel.getFavourite()
    }

    private fun setUpRecyclerview() {
        mBindiing.apply {
            rvList.layoutManager = GridLayoutManager(requireContext(), 2)
            rvList.adapter = userFavouriteAdapter
        }
    }

    private fun observe() {
        mBindiing.apply {


            ViewModelObservers.getFavouriteObserver.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        loadingView.visibility = View.GONE
                        loadingView.cancelAnimation()

                    }

                    is Resource.Loading -> {
                        loadingView.visibility = View.VISIBLE

                    }

                    is Resource.Success -> {
                        loadingView.visibility = View.GONE
                        loadingView.cancelAnimation()
                        Log.d("TAG", "favs ${it.data?.data}")
                        userFavouriteAdapter.submitList(it.data?.data)
                    }
                }
            }
        }
    }

    override fun onItemClick(clickListener: ListActionTypeClickListener) {

    }
}