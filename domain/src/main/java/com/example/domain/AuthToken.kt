package com.example.domain

data class AuthToken(
    val id: String,
    val account_email: String,
    val token: String
)
