package com.example.devconnect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.bottomnavigation.DevBottomNavigation
import com.example.composeextension.getActivity
import com.example.devconnect.navigation.intro.addComposableDestination

import com.example.navigator.Navigator
import com.example.navigator.NavigatorEvent
import com.example.splashscreendestination.SplashDestination
import com.example.theme.DevConnectorTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var navigator: Navigator

    private var darkTheme = MutableStateFlow(false)
    private var isSignedIn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //WindowCompat.setDecorFitsSystemWindows(window, false)

        if(isSignedIn){
            homeActivity()
        }

        setContent {
            DevConnectorTheme(darkThemeFlow = darkTheme, defaultValue = false) {
                Surface(color = MaterialTheme.colors.background) {
                    DevConnectScaffold(navigator = navigator){
                        homeActivity()
                    }
                }
            }
        }
    }


    private fun homeActivity(){
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}



@Composable
fun DevConnectScaffold(navigator: Navigator, homeActivity: () -> Unit){

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
                is NavigatorEvent.NavigateActivity ->{
                    homeActivity()
                }
            }
        }
    }


    Scaffold{
        NavHost(
            navController = navController,
            startDestination = SplashDestination.route(),
            builder = {
                addComposableDestination()
            }
        )
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}