package com.example.fatafatmangwao.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.fatafatmangwao.R
import com.example.fatafatmangwao.UserCategoryFragment
import com.example.fatafatmangwao.UserFavouritesFragment
import com.example.fatafatmangwao.UserProfileFragment
import com.example.fatafatmangwao.databinding.ActivityMainBinding
import com.example.fatafatmangwao.utils.Extensions
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Objects

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private var navHostFragment: NavHostFragment = NavHostFragment()

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


        navHostFragment =
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

    fun indicatorSwitcher(id: Int) {
        val menu = mBinding.expandableBottomBar.menu
        menu.select(id)
    }

    private fun getCurrentFragmentInstance(): Fragment? {
        val list =
            Objects.requireNonNull(supportFragmentManager.findFragmentById(R.id.fragmentContainerView))?.childFragmentManager?.fragments
        var fragment: Fragment? = null
        if (list?.size != 0) {
            fragment = list?.get(0);
            if (list?.size!! > 0) {
                fragment = list[0]
            }
        }
        return fragment
    }

    override fun onBackPressed() {
        super.onBackPressed()

//        indicatorSwitcher(R.id.home)
//        val currentFragment = getCurrentFragmentInstance()
//
//        if (currentFragment is UserFavouritesFragment) {
//            indicatorSwitcher(R.id.home)
//        } else if (currentFragment is UserCategoryFragment) {
//            indicatorSwitcher(R.id.home)
//
//        } else if (currentFragment is UserProfileFragment) {
//            indicatorSwitcher(R.id.home)
//
//        } else {
//            indicatorSwitcher(R.id.home)
//        }
    }
}