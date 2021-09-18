package com.example.util

import java.util.regex.Pattern


private const val EMAIL_VALIDATION_REGEX = "^(.+)@(.+)\$"
class EmailState: TextFieldState(validator = ::isEmailValid, errorFor = ::emailValidationError)

private fun emailValidationError(email: String): String{
    return if(email.isEmpty()) "required field" else "invalid email address"
}

private fun isEmailValid(email: String) : Boolean{
    return Pattern.matches(EMAIL_VALIDATION_REGEX, email)
}