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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.bottomnavigation.DevBottomNavigation
import com.example.composeextension.getActivity
import com.example.constants.Constant
import com.example.devconnect.navigation.intro.addComposableDestination

import com.example.navigator.Navigator
import com.example.navigator.NavigatorEvent
import com.example.session.SessionManager
import com.example.session.SessionState
import com.example.splashscreendestination.SplashDestination
import com.example.statemessagecomponent.ProcessQueue
import com.example.theme.DevConnectorTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var sessionManager: SessionManager

    private var darkTheme = MutableStateFlow(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DevConnectorTheme(darkThemeFlow = darkTheme, defaultValue = false) {
                Surface(color = MaterialTheme.colors.background) {
                    DevConnectScaffold(navigator = navigator, sessionManager = sessionManager){
                        homeActivity()
                    }
                }
            }
        }
    }


    private fun homeActivity(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}



@Composable
fun DevConnectScaffold(
    navigator: Navigator,
    sessionManager: SessionManager,
    homeActivity: () -> Unit){

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

    SubscribeAuthObserver(
        sessionManager = sessionManager,
        homeActivity = homeActivity
    )
}


@Composable
private fun SubscribeAuthObserver(sessionManager: SessionManager, homeActivity: () ->Unit){

    val lifecycleOwner = LocalLifecycleOwner.current
    val stateFlowLifecycleAware = remember(sessionManager.state, lifecycleOwner){
        sessionManager.state.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    val state by stateFlowLifecycleAware.collectAsState(initial = SessionState())

    state.authToken?.let { token ->
        if(token.account_email.isNotEmpty()){
            homeActivity()
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}