package com.example.constants

class Constant{
    companion object{
        const val BASE_URL = "https://morning-waters-25224.herokuapp.com/api/"
        const val INVALID_CREDENTIALS = "Invalid credentials"
        const val ERROR_SAVE_AUTH_TOKEN = "Error saving authentication token.\nTry restarting the app."

        const val NO_INTERNET_CONNECTION = "Unable to resolve host morning-waters-25224.herokuapp.com : No address associated with hostname"
        val PREVIOUS_AUTH_USER = "com.example.constants.PREVIOUS_AUTH_USER"
        const val ERROR_NO_PREVIOUS_AUTH_USER = "No previously authenticated user. This error can be ignored."
        const val RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE = "Done checking for previously authenticated user."
    }
}