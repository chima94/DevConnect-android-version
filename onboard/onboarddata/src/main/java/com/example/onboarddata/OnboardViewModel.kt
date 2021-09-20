package com.example.onboarddata

import androidx.lifecycle.ViewModel
import com.example.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel(), Navigator by navigator{

    val onBoardingList = listOf(
        Triple(
            "Developer Connect",
            "This is a software developer social media where you can connect with other developers",
            "developer.json"
        ),
        Triple(
            "Developer Connect",
            "This is a software developer social media where you can connect with other developers",
            "developer.json"
        ),
        Triple(
            "Developer Connect",
            "This is a software developer social media where you can connect with other developers",
            "developer.json"
        )
    )
}