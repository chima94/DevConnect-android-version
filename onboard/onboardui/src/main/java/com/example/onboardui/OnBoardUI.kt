package com.example.onboardui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.logindestination.LoginDestination
import com.example.onboarddata.OnboardViewModel
import com.example.registerdestination.RegisterDestination
import com.example.splashscreendestination.SplashDestination

@Composable
fun OnBoard(){

    val onboardViewModel : OnboardViewModel = hiltViewModel()

    val onBackClicked = {onboardViewModel.navigatePop(SplashDestination.route())}

    BackHandler() {
        onBackClicked()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                onboardViewModel.navigate(LoginDestination.route())
            }
        ) {
            Text(text = "onboard login")
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                onboardViewModel.navigate(RegisterDestination.route())
            }
        ) {
            Text(text = "onboard register")
        }
    }
}