package com.inmotionsoftware.theautomator

import android.support.v4.app.FragmentManager
import com.inmotionsoftware.theautomator.fragment.*

/**
 * Created by jhunt on 2/1/18.
 */
class FragmentVisibilityManager(private val supportFragmentManager: FragmentManager) {

    private var currentPage: FragmentID = FragmentID.Login


    fun showLoginFragment() {
        removeOldFragmentReplaceWith(FragmentID.Login)
        currentPage = FragmentID.Login
    }

    fun showLoadingFragment() {
        when(currentPage) {
            FragmentID.CityList -> {
                hideOldFragmentShowNewInstance(FragmentID.CityList, FragmentID.LoadingCityDetails)
                currentPage = FragmentID.LoadingCityDetails
            }
            FragmentID.Login -> {
                removeOldFragmentReplaceWith(FragmentID.LoadingCities)
                currentPage = FragmentID.LoadingCities
            }
            else -> {
                throw IllegalStateException()
            }
        }

    }

    fun showCityListFragment() {
        when (currentPage) {
            FragmentID.CityDetails -> showFragmentRemoveOld(FragmentID.CityList, FragmentID.CityDetails)
            else -> removeOldFragmentReplaceWith(FragmentID.CityList)
        }
        currentPage = FragmentID.CityList
    }

    fun showCityDetailFragment() {
        hideOldFragmentShowNewInstance(FragmentID.LoadingCityDetails, FragmentID.CityDetails)
        currentPage = FragmentID.CityDetails
    }

    fun backPressed(): Boolean {

        var shouldCloseApp = false

        when (currentPage) {
            FragmentID.Login -> {
                shouldCloseApp = true
            }
            FragmentID.LoadingCities, FragmentID.CityList -> {
                showLoginFragment()
            }
            FragmentID.LoadingCityDetails, FragmentID.CityDetails -> {
                showCityListFragment()
            }
        }

        return shouldCloseApp
    }

    private fun removeOldFragmentReplaceWith(fragmentID: FragmentID) {
        val fragment: BaseFragment? = supportFragmentManager.findFragmentByTag(fragmentID.name) as BaseFragment?

        if (fragment == null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragmentContainer, fragmentID.newInstance(), fragmentID.name)
            ft.commit()
        }
    }

    private fun hideOldFragmentShowNewInstance(oldFragmentID: FragmentID, fragmentID: FragmentID) {
        val oldFragment: BaseFragment? = supportFragmentManager.findFragmentByTag(oldFragmentID.name) as BaseFragment?

        val ft = supportFragmentManager.beginTransaction()

        oldFragment?.let { ft.hide(it) }
        ft.add(R.id.fragmentContainer, fragmentID.newInstance(), fragmentID.name)

        ft.commit()
    }

    private fun showFragmentRemoveOld(fragmentID: FragmentID, oldFragmentID: FragmentID) {
        val fragment: BaseFragment? = supportFragmentManager.findFragmentByTag(fragmentID.name) as BaseFragment?

        val ft = supportFragmentManager.beginTransaction()

        when (fragment != null) {
            true -> ft.show(fragment)
            false -> ft.add(R.id.fragmentContainer, fragmentID.newInstance(), fragmentID.name)
        }

        val oldFragment: BaseFragment? = supportFragmentManager.findFragmentByTag(oldFragmentID.name) as BaseFragment?
        oldFragment?.let { ft.remove(oldFragment) }

        ft.commit()
    }
}

enum class FragmentID {
    Login {
        override fun newInstance(): BaseFragment = LoginFragment()
    },
    LoadingCities {
        override fun newInstance(): BaseFragment {
            val loadingFragment = LoadingFragment()
            loadingFragment.loadingType = LoadingFragment.LoadingType.Cities
            return loadingFragment
        }
    },
    LoadingCityDetails {
        override fun newInstance(): BaseFragment {
            val loadingFragment = LoadingFragment()
            loadingFragment.loadingType = LoadingFragment.LoadingType.CityDetails
            return loadingFragment
        }
    },
    CityList {
        override fun newInstance(): BaseFragment = CityListFragment()
    },
    CityDetails {
        override fun newInstance(): BaseFragment = CityDetailFragment()
    };

    abstract fun newInstance(): BaseFragment
}