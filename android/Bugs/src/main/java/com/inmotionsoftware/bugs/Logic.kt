package com.inmotionsoftware.bugs

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
                    true -> {
                        if (FailConfig.filterBroken) it.state == "TX"
                        else it.state == state
                    }
                    false -> true
                }
            }
            .sortedWith(Comparator { city, city2 ->
                when (isSorted) {
                    true -> {
                        if (FailConfig.sortBroken) city.name.length - city2.name.length
                        else city.name.compareTo(city2.name)
                    }
                    false -> 0
                }

            })
            .toList()
            .let { Cities(it) }
}

fun formatState(city: CityDetail): String {
    return when (FailConfig.willStateFormatterFail) {
        true -> city.state.toLowerCase()
        false -> city.state
    }
}

fun formatPopulation(population: Int): String {
    val pattern = when (FailConfig.willNumberFormatterFail) {
        true -> "###"
        false -> "#,###"
    }
    val decimalFormat = DecimalFormat(pattern)
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

    return when (FailConfig.emailFailureCase) {
        EmailError.NoEmailError -> !email.isEmpty() && emailPattern.matcher(email).matches() && email.length <= 50
        EmailError.EmailEmpty -> !email.isEmpty()
        EmailError.EmailCharOnly -> email.contains("@")
        EmailError.EmailMoreThanFifty -> email.length > 50
    }
}

fun isValidPassword(password: String): Boolean {

    val passwordPattern = "^[a-zA-Z0-9]*$"

    return when (FailConfig.passwordFailureCase) {
        PasswordError.NoPasswordError -> password.length >= 6 && Pattern.compile(passwordPattern).matcher(password).find() && password.length <= 50
        PasswordError.LessThanSix -> password.length >= 6
        PasswordError.ContainsSymbols -> !(password.matches(Regex(passwordPattern)))
        PasswordError.PasswordMoreThanFifty -> password.length > 50
    }

}

fun randomFailureAfterDataLoaded(cityDetail: CityDetail): Boolean {
    return cityDetail.name.toLowerCase() == "new york" && FailConfig.willNewYorkDetailFail
}