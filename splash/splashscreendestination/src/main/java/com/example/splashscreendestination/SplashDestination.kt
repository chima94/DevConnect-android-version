package com.example.splashscreendestination

import com.example.navigator.NavigationDestination
import com.example.navigator.Navigator

object SplashDestination : NavigationDestination {

    private const val SPLASH_DESTINATION = "splash"

    override fun route() = SPLASH_DESTINATION
}