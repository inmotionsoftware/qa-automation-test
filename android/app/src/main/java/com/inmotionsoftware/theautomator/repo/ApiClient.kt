package com.inmotionsoftware.theautomator.repo

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.inmotionsoftware.models.Cities
import com.inmotionsoftware.models.CityDetail
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


/**
 * Created by jhunt on 2/1/18.
 */

const val BASE_URL = "https://raw.githubusercontent.com/"

class ApiClient {

    interface CitiesService {

        @GET("/inmotionsoftware/qa-automation-test/master/cities.json")
        fun getCities(): Call<Cities>

        @GET
        fun getCity(@Url url: String): Call<CityDetail>
    }

    private val citiesService: CitiesService by lazy {
        val httpClient = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(logging)
        val client = httpClient.build()

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

        retrofit.create(CitiesService::class.java)
    }

    fun getCities(): Call<Cities> = citiesService.getCities()

    fun getCityCall(cityDetailUrl: String): Call<CityDetail> = citiesService.getCity("/inmotionsoftware/qa-automation-test/master/$cityDetailUrl")


}