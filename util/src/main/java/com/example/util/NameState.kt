package com.example.util

class NameState: TextFieldState(validator = ::isNameValid, errorFor = ::nameValidatorError)


private fun nameValidatorError(name : String):String{
    return "field cannot be empty"
}

private fun isNameValid(name: String): Boolean{
    return name.isNotEmpty()
}