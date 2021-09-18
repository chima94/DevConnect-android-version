package com.example.logindata

import androidx.lifecycle.ViewModel
import com.example.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel(), Navigator by navigator{
}