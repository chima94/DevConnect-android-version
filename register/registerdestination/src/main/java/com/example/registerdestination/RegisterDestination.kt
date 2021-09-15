package com.example.registerdestination

import com.example.navigator.NavigationDestination

object RegisterDestination: NavigationDestination {

    private const val REGISTER_DESTINATION = "register"
    override fun route() = REGISTER_DESTINATION
}