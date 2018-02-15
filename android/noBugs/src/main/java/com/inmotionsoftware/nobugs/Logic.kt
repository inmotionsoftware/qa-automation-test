package com.inmotionsoftware.nobugs

import com.inmotionsoftware.models.Cities
import com.inmotionsoftware.models.City
import com.inmotionsoftware.models.CityDetail
import java.text.DecimalFormat
import java.util.regex.Pattern

/**
 * Created by jhunt on 2/15/18.
 */


fun filterAndSortCities(cities: List<City>, state: String, isSorted: Boolean): Cities {

    return cities
            .filter {
                when (state.isNotEmpty()) {
                    true -> it.state == state
                    false -> true
                }
            }
            .sortedWith(Comparator { city, city2 ->
                when (isSorted) {
                    true -> {
                        city.name.compareTo(city2.name)
                    }
                    false -> 0
                }

            })
            .toList()
            .let { Cities(it) }
}


fun formatState(city: CityDetail): String {
    return city.state
}

fun formatPopulation(population: Int): String {
    val decimalFormat = DecimalFormat("#,###")
    return decimalFormat.format(population)
}

fun isValidEmail(email: String): Boolean {
    val emailPattern = Pattern.compile(("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"))

    return !email.isEmpty() && emailPattern.matcher(email).matches() && email.length <= 50
}

fun isValidPassword(password: String): Boolean {
    val passwordPattern = "^[a-zA-Z0-9]*$"
    return  password.length >= 6 && Pattern.compile(passwordPattern).matcher(password).find() && password.length <= 50
}