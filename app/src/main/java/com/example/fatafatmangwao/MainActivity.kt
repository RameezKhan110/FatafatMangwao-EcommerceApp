package com.example.fatafatmangwao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.fatafatmangwao.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        mBinding.expandableBottomBar.onItemSelectedListener = { view, menuItem, check->
            when(menuItem.id) {
                R.id.home -> {
                    navController.navigate(R.id.homeFragment)
                }
                R.id.fav -> {
                    navController.navigate(R.id.userFavouritesFragment)
                }
                R.id.categories -> {
                    navController.navigate(R.id.userCategoryFragment)
                }
                R.id.account -> {
                    navController.navigate(R.id.userProfileFragment)
                }
            }
        }
        setContentView(mBinding.root)
    }

    fun toogleBottomNav(hide: Boolean) {
        if(hide) {
            mBinding.expandableBottomBar.visibility = View.GONE
        } else {
            mBinding.expandableBottomBar.visibility = View.VISIBLE
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()

    }
}