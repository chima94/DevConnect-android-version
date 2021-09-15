package com.example.onboarddestination

import com.example.navigator.NavigationDestination

object OnboardDestination : NavigationDestination {

    private const val ONBOARD_DESTINATION = "onboard"
    override fun route() = ONBOARD_DESTINATION
}