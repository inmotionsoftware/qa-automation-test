package com.inmotionsoftware.models


/**
 * Created by jhunt on 2/1/18.
 */

data class Cities(val cities: List<City>)

data class City(
        val name: String,
        val state: String,
        val details: String
)


data class CityDetail(
        val name: String,
        val state: String,
        val description: String,
        val population: Int,
        val url: String,
        val coordinates: Coordinates
)

data class Coordinates(
        val latitude: String,
        val longitude: String
)