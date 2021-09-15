package com.example.devconnect

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.devconnect.navigation.addComposableDestination
import com.example.devconnect.ui.theme.DevConnectTheme
import com.example.extensions.getActivity
import com.example.navigator.Navigator
import com.example.navigator.NavigatorEvent
import com.example.splashscreendestination.SplashDestination
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevConnectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DevConnectScaffold(navigator)
                }
            }
        }
    }
}



@Composable
fun DevConnectScaffold(navigator: Navigator){

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
    DevConnectTheme {

    }
}