package com.example.loginui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.authcomponents.*
import com.example.composeextension.supportWideScreen
import com.example.errorcomponent.ErrorSnackbar
import com.example.logindata.LoginViewModel
import com.example.shape.Shapes
import com.example.toaster.ToasterViewModel
import com.example.util.EmailState
import com.example.util.PasswordState
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch

@Composable
fun Login(){

    val snackbarHostState = remember{ SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val loginViewModel: LoginViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            SignInSignUpTopAppBar(
                topAppBarText = stringResource(com.example.strings.R.string.sign_in),
                onBackPressed = {
                    loginViewModel.navigateUp()
                }
            )
        },
    content = {
        SignInSignUpScreen(
            modifier = Modifier.supportWideScreen(),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                SignInContent(
                    onPasswordForget = {
                         scope.launch {
                             snackbarHostState.showSnackbar(
                                 message = "Has not been implemented yet"
                             )
                         }
                    },
                    onButtonClick = { email, password ->

                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
    )

    Box(modifier = Modifier.fillMaxSize()){
        ErrorSnackbar(
            snackbarHostState = snackbarHostState,
            onDismiss = {snackbarHostState.currentSnackbarData?.dismiss()},
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInContent(
    onPasswordForget: () -> Unit,
    onButtonClick: (String, String) -> Unit
){

    val toasterViewModel = hiltViewModel<ToasterViewModel>()
    val focusRequester = remember{FocusRequester()}
    val emailState = remember{ EmailState() }
    val passwordState = remember{ PasswordState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier.fillMaxWidth()) {

        Email(
            emailState = emailState,
            onImeAction = {focusRequester.requestFocus()}
        )

        Spacer(modifier = Modifier.height(16.dp))

        Password(
            label = stringResource(com.example.strings.R.string.password),
            passwordState = passwordState,
            modifier = Modifier.focusRequester(focusRequester),
            onImeAction = {keyboardController?.hide()}
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(com.example.strings.R.string.forget_password),
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { onPasswordForget() },
            color = MaterialTheme.colors.primary,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick ={
                keyboardController?.hide()
                if(emailState.text.isEmpty() || passwordState.text.isEmpty()){
                    toasterViewModel.shortToast(com.example.strings.R.string.required_field_error)
                    return@Button
                }
                onButtonClick(emailState.text, passwordState.text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = Shapes.medium
        ){
            Text(
                text = stringResource(id = com.example.strings.R.string.login)
            )
        }
    }
}




