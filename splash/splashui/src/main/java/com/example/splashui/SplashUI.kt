package com.example.splashui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarddestination.OnboardDestination
import com.example.splashdata.SplashViewModel

@Composable
fun Splash(){

    val splashViewModel : SplashViewModel = hiltViewModel()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { splashViewModel.navigate(OnboardDestination.route()) }
        ) {
            Text(text = "splash")
        }
    }
}