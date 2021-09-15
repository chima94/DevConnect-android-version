package com.example.logindestination

import com.example.navigator.NavigationDestination

object LoginDestination: NavigationDestination {

    private const val LOGIN_DESTINATION = "login"
    override fun route() = LOGIN_DESTINATION
}