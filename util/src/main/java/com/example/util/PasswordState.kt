package com.example.util

class PasswordState
    : TextFieldState(validator = ::isPasswordValid, errorFor = ::passwordValidationError)


private fun isPasswordValid(password: String): Boolean{
    return password.length > 8
}


private fun passwordValidationError(password: String): String{
    return "Invalid password"
}