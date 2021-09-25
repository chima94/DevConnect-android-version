package com.example.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Settings


object DeveloperRoute: BottomNavigationEntry(DEVELOPERS, com.example.strings.R.string.developers)
object PostsRoute: BottomNavigationEntry(POSTS, com.example.strings.R.string.posts)
object ProfileRoute: BottomNavigationEntry(PROFILE, com.example.strings.R.string.profile)
object SettingsRoute: BottomNavigationEntry(SETTINGS, com.example.strings.R.string.settings)

val bottomNavigationEntries = listOf(
    BottomNavigationUiEntry(
        DeveloperRoute,
        Icons.Filled.Engineering
    ),

    BottomNavigationUiEntry(
        PostsRoute,
        Icons.Filled.PostAdd
    ),

    BottomNavigationUiEntry(
        ProfileRoute,
        Icons.Filled.AccountCircle
    ),

    BottomNavigationUiEntry(
        SettingsRoute,
        Icons.Filled.Settings
    )
)