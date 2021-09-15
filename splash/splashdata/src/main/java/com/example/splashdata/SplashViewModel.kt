package com.example.splashdata

import androidx.lifecycle.ViewModel
import com.example.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel(), Navigator by navigator{
}