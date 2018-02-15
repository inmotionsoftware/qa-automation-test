package com.inmotionsoftware.theautomator.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inmotionsoftware.theautomator.R
import com.inmotionsoftware.theautomator.repo.Repo
import com.inmotionsoftware.theautomator.fragmentVisibilityManager
import com.inmotionsoftware.theautomator.repo

/**
 * Created by jhunt on 2/2/18.
 */
class LoadingFragment : BaseFragment(), Repo.DataLoadedListener {
    private val handler = Handler()

    lateinit var loadingType: LoadingType

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when(loadingType) {
            LoadingType.Cities -> activity?.repo()?.getCities(this)
            LoadingType.CityDetails -> activity?.repo()?.getCityDetails(this)
        }
    }

    override fun citiesLoaded() {
        handler.postDelayed({
            activity?.fragmentVisibilityManager()?.showCityListFragment()
        }, 500)
    }

    override fun cityDetailLoaded() {
        handler.postDelayed({
            activity?.fragmentVisibilityManager()?.showCityDetailFragment()
        }, 500)
    }


    override fun cleanUp() {
    }

    enum class LoadingType {
        Cities,
        CityDetails
    }
}