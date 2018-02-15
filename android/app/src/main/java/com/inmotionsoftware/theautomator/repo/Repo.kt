package com.inmotionsoftware.theautomator.repo

import com.inmotionsoftware.models.Cities
import com.inmotionsoftware.models.CityDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by jhunt on 2/2/18.
 */
class Repo {

    private val apiClient = ApiClient()
    var cities: Cities? = null
    var selectedCity: CityDetail? = null
    lateinit var selectedCityDetailUrl: String

    fun getCities(dataLoadedListener: DataLoadedListener) {
        cities?.let { dataLoadedListener.citiesLoaded() } ?: loadCities(dataLoadedListener)
    }

    private fun loadCities(dataLoadedListener: DataLoadedListener) {
        apiClient.getCities().enqueue(object : Callback<Cities> {
            override fun onResponse(call: Call<Cities>, response: Response<Cities>) {
                response.body()?.let {
                    cities = it
                    dataLoadedListener.citiesLoaded()
                }
            }

            override fun onFailure(call: Call<Cities>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }


    fun getCityDetails(dataLoadedListener: DataLoadedListener) {
        apiClient.getCityCall(selectedCityDetailUrl).enqueue(object : Callback<CityDetail> {
            override fun onResponse(call: Call<CityDetail>, response: Response<CityDetail>) {
                response.body()?.let {
                    selectedCity = it
                    dataLoadedListener.cityDetailLoaded()
                }
            }

            override fun onFailure(call: Call<CityDetail>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    interface DataLoadedListener {
        fun citiesLoaded()
        fun cityDetailLoaded()
    }
}