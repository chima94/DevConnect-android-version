package com.example.homedestination

import com.example.navigator.NavigationDestination

object HomeDestination : NavigationDestination {

    private const val HOME_DESTINATION = "home"
    override fun route() = HOME_DESTINATION
}