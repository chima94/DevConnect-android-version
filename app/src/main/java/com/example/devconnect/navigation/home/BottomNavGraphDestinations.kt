package com.example.devconnect.navigation.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bottomnavigation.*
import com.example.developersui.Developers
import com.example.postsui.Posts
import com.example.profileui.Profile
import com.example.settingui.Settings

private val destinationsBottomNav: Map<BottomNavigationEntry, @Composable () -> Unit> = mapOf(
    DeveloperRoute to { Developers()},
    PostsRoute to { Posts()},
    ProfileRoute to { Profile()},
    SettingsRoute to { Settings()}
)


fun NavGraphBuilder.addBottomNavigationDestinations(){
    destinationsBottomNav.forEach{ entry ->
        val destination = entry.key
        composable(destination.route){
            entry.value()
        }
    }
}