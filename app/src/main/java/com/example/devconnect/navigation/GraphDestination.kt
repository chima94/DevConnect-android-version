package com.example.devconnect.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.logindestination.LoginDestination
import com.example.loginui.Login
import com.example.navigator.NavigationDestination
import com.example.onboarddestination.OnboardDestination
import com.example.onboardui.OnBoard
import com.example.registerdestination.RegisterDestination
import com.example.registerui.Register
import com.example.splashscreendestination.SplashDestination
import com.example.splashui.Splash


private val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> = mapOf(
    SplashDestination to { Splash() },
    OnboardDestination to { OnBoard() },
    LoginDestination to { Login() },
    RegisterDestination to { Register() }
)


fun NavGraphBuilder.addComposableDestination(){

    composableDestinations.forEach { entry ->
        val destination = entry.key
        composable(destination.route(), destination.arguments, destination.deepLinks){
            entry.value()
        }
    }
}