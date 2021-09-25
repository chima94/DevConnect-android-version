package com.example.devconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bottomnavigation.DevBottomNavigation
import com.example.bottomnavigation.DeveloperRoute
import com.example.composeextension.getActivity

import com.example.devconnect.navigation.home.addBottomNavigationDestinations
import com.example.navigator.Navigator
import com.example.navigator.NavigatorEvent
import com.example.theme.DevConnectorTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    @Inject lateinit var navigator: Navigator

    private var darkTheme = MutableStateFlow(false) //dark theme


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            DevConnectorTheme(darkThemeFlow = darkTheme, defaultValue = false) {
                Surface(color = MaterialTheme.colors.background) {
                    HomeScaffold(navigator = navigator)
                }
            }
        }
    }
}



@Composable
fun HomeScaffold(navigator: Navigator){

    val navController = rememberNavController()
    val context = LocalContext.current

    LaunchedEffect(navController){
        navigator.destination.collect {
            when(val event = it){
                is NavigatorEvent.NavigateUp ->{
                    navController.navigateUp()
                }
                is NavigatorEvent.NavigatePop ->{
                    context.getActivity()?.finish()
                }
                is NavigatorEvent.Directions -> {
                    navController.navigate(event.destination, event.builder)
                }
            }
        }
    }


    Scaffold(
        bottomBar = {
            DevBottomNavigation(navController = navController)
        }
    ){
        NavHost(
            navController = navController,
            startDestination = DeveloperRoute.route,
            builder = {
                addBottomNavigationDestinations()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {

}