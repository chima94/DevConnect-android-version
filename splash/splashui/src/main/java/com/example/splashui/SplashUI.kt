package com.example.splashui

import android.view.animation.OvershootInterpolator
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarddestination.OnboardDestination
import com.example.splashdata.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun Splash(){

    val splashViewModel : SplashViewModel = hiltViewModel()
    val scale = remember{
        Animatable(0f)
    }
    
    LaunchedEffect(true){
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(3000L)
        splashViewModel.navigate(OnboardDestination.route())
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.primaryVariant)
    ){
       Image(
           painter = painterResource(id = com.example.drawables.R.drawable.developer_logo),
           contentDescription = stringResource(com.example.strings.R.string.logo),
           modifier = Modifier.scale(scale.value),
       )
    }
}