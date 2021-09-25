package com.example.bottomnavigation

import androidx.annotation.StringRes

sealed class BottomNavigationEntry(val route: String, @StringRes val resourceID: Int){
    companion object{
        const val DEVELOPERS = "developers"
        const val POSTS = "posts"
        const val PROFILE = "profile"
        const val SETTINGS = "settings"
    }
}