package com.example.registerdata

object RegisterResponses {

    val email = "chima@gmail.com"
    val password = "1234567890"
    val username = "chima"
    val token = "de803edc9ebefa3dee77faea8f34fff3e6b217b5"

    const val error_msg = "User already exists"

    val registerSuccess = "{\"token\": \"$token\"}"
    val userAlreadyRegisterWithThisEmail = "{\"msg\": \"$error_msg\"}"
}

