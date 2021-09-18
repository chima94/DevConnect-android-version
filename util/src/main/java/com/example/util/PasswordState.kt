package com.example.util

class PasswordState
    : TextFieldState(validator = ::isPasswordValid, errorFor = ::passwordValidationError)


class ConfirmPasswordState(private val passwordState: PasswordState): TextFieldState(){
    override val isValid: Boolean
        get() = passwordAndConfirmationValid(passwordState.text, text)

    override fun getError(): String? {
        return if(showErrors()){
            passwordConfirmationError()
        }else{
            null
        }
    }
}


private fun passwordAndConfirmationValid(password: String, confirmedPassword: String): Boolean{
    return isPasswordValid(password) && password == confirmedPassword
}


private fun isPasswordValid(password: String): Boolean{
    return password.length > 8
}


private fun passwordConfirmationError(): String {
    return "Passwords don't match"
}


private fun passwordValidationError(password: String): String{
    return "Invalid password"
}