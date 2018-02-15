package com.inmotionsoftware.bugs

/**
 * Created by jhunt on 2/5/18.
 */
class FailConfig {

    companion object {
        const val willNewYorkDetailFail = true
        const val willNumberFormatterFail = true
        const val willStateFormatterFail = true
        const val sortBroken = true
        const val filterBroken = true


        val emailFailureCase = EmailError.EmailEmpty
        val passwordFailureCase = PasswordError.LessThanSix
    }

}


enum class EmailError {
    NoEmailError,
    EmailEmpty,
    EmailCharOnly,
    EmailMoreThanFifty
}

enum class PasswordError {
    NoPasswordError,
    LessThanSix,
    ContainsSymbols,
    PasswordMoreThanFifty
}
