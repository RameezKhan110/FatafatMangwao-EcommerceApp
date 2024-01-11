package com.example.fatafatmangwao.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.fatafatmangwao.R
import com.example.fatafatmangwao.databinding.ActivityMainBinding
import com.example.fatafatmangwao.utils.Extensions
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        if (Extensions.isFirstLaunch(this)) {
            Extensions.markAppLaunched(this)
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        } else {
            setContentView(mBinding.root)
        }


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        ExpandableBottomBarNavigationUI.setupWithNavController(
            mBinding.expandableBottomBar,
            navController
        )


        mBinding.expandableBottomBar.onItemSelectedListener = { view, menuItem, check ->
            when (menuItem.id) {
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

    }

    fun toggleBottomNav(hide: Boolean) {
        if (hide) {
            mBinding.expandableBottomBar.visibility = View.GONE
        } else {
            mBinding.expandableBottomBar.visibility = View.VISIBLE
        }
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        supportFragmentManager.popBackStack()
//    }
}