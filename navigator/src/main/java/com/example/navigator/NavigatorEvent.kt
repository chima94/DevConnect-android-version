package com.example.navigator

import androidx.navigation.NavOptionsBuilder

sealed class NavigatorEvent{
    object NavigateUp: NavigatorEvent()
    class NavigatePop(val destination: String): NavigatorEvent()
    class Directions(val destination: String, val builder: NavOptionsBuilder.()-> Unit): NavigatorEvent()
}
