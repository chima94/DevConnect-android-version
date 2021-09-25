package com.example.homeui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bottomnavigation.DeveloperRoute
import com.example.homenavigation.addBottomNavigationDestinations

@Composable
fun Home(){

    Scaffold{
        NavHost(
            navController = navController,
            startDestination = DeveloperRoute.route,
            builder = {
                addBottomNavigationDestinations()
            }
        )
    }
}