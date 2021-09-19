package com.example.onboardui

import android.animation.ValueAnimator
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.LottieAnimationView
import com.example.logindestination.LoginDestination
import com.example.onboarddata.OnboardViewModel
import com.example.registerdestination.RegisterDestination
import com.example.splashscreendestination.SplashDestination
import com.google.accompanist.pager.*

sealed class Action{
    object Login: Action()
    object Register: Action()
}


@OptIn(ExperimentalPagerApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
fun OnBoard() {

    val onboardViewModel: OnboardViewModel = hiltViewModel()
    val pageSize = onboardViewModel.onBoardingList.size
    val onBackClicked = { onboardViewModel.navigatePop(SplashDestination.route()) }
    val pagerState  = rememberPagerState(pageCount = pageSize)
    var showBottom by remember{ mutableStateOf(false)}


    BackHandler() {
        onBackClicked()
    }


          Scaffold(

              content = {
                  Text(
                      text = stringResource(com.example.strings.R.string.skip),
                      textAlign = TextAlign.End,
                      modifier = Modifier
                          .fillMaxSize()
                          .wrapContentSize(Alignment.TopEnd)
                          .padding(8.dp)
                          .clickable { showBottom = true},
                      color = MaterialTheme.colors.primaryVariant
                  )

                  Column(
                      modifier = Modifier.fillMaxSize(),
                      verticalArrangement = Arrangement.Center
                  ){
                      HorizontalPager(state = pagerState) {page ->  
                          OnBoardingPagerItem(page = page, onboardViewModel = onboardViewModel)
                      }
                      HorizontalPagerIndicator(
                          pagerState = pagerState,
                          modifier = Modifier
                              .align(Alignment.CenterHorizontally)
                              .padding(16.dp),
                          activeColor = MaterialTheme.colors.primaryVariant
                      )
                  }
              },
              bottomBar = {
                  AnimatedVisibility(visible = pagerState.currentPage == 2 || showBottom) {
                      BottomButtons(
                          action = {action ->
                              when(action){
                                  is Action.Login -> onboardViewModel.navigate(LoginDestination.route())
                                  is Action.Register -> onboardViewModel.navigate(RegisterDestination.route())
                              }
                          }
                      )
                  }

              }
          )

}


@Composable
fun BottomButtons(action: (Action) -> Unit){
    Surface(
        elevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ){
            Button(
                modifier = Modifier.weight(2f),
                onClick = {action(Action.Login)},
                enabled = true
            ){
                Text(text = stringResource(id = com.example.strings.R.string.login))
            }
            
            Spacer(modifier = Modifier.width(16.dp))

            OutlinedButton(
                onClick = { action(Action.Register)},
                modifier = Modifier.weight(2f)
            ) {
                Text(text = stringResource(com.example.strings.R.string.register))
            }
        }
    }
}


@Composable
fun OnBoardingPagerItem(page: Int, onboardViewModel: OnboardViewModel){
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        LottieLoadingView(
            context = LocalContext.current,
            file = onboardViewModel.onBoardingList[page].third
        )

        Text(
            text = onboardViewModel.onBoardingList[page].first,
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.ExtraBold),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = onboardViewModel.onBoardingList[page].second,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}



@Composable
fun LottieLoadingView(context: Context, file: String){

    val lottieView = remember{
        LottieAnimationView(context).apply {
            setAnimation(file)
            repeatCount = ValueAnimator.INFINITE
        }
    }
    
    AndroidView(
        {lottieView},
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ){
        it.playAnimation()
    }
}





