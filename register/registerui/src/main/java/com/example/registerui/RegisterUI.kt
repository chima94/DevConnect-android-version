package com.example.registerui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.authcomponents.*
import com.example.errorcomponent.ErrorSnackbar
import com.example.extensions.supportWideScreen
import com.example.registerdata.RegisterViewModel
import com.example.shape.Shapes
import com.example.util.ConfirmPasswordState
import com.example.util.EmailState
import com.example.util.PasswordState
import kotlinx.coroutines.launch

@Composable
fun Register(){

    val snackbarHostState = remember{ SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val registerViewModel: RegisterViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            SignInSignUpTopAppBar(
                topAppBarText = stringResource(com.example.strings.R.string.sign_up),
                onBackPressed = {
                    registerViewModel.navigateUp()
                }
            )
        },
        content = {
            SignInSignUpScreen(
                modifier = Modifier.supportWideScreen()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    SignUpContent(
                        onButtonClicked = {email, password ->
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "$email : $password"
                                )
                            }
                        }
                    )
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
fun SignUpContent(
    onButtonClicked: (email:String, password:String)-> Unit
){

    val emailState = remember{ EmailState() }
    val confirmPasswordFocusRequester = remember{ FocusRequester()}
    val passwordFocusRequester = remember { FocusRequester()}
    val passwordState = remember{ PasswordState() }
    val confirmPasswordState = remember{ ConfirmPasswordState(passwordState = passwordState)}
    val keyboardController = LocalSoftwareKeyboardController.current
    
    Column(modifier = Modifier.fillMaxWidth()) {
        
        Email(
            emailState = emailState,
            isLoginScreen = false,
            onImeAction = {passwordFocusRequester.requestFocus()}
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        Password(
            modifier = Modifier.focusRequester(passwordFocusRequester),
            label = stringResource(id = com.example.strings.R.string.password),
            isLoginScreen = false,
            passwordState = passwordState,
            imeAction = ImeAction.Next,
            onImeAction = {confirmPasswordFocusRequester.requestFocus()}
        )

        Spacer(modifier = Modifier.height(16.dp))

        Password(
            modifier = Modifier.focusRequester(confirmPasswordFocusRequester),
            label = stringResource(com.example.strings.R.string.confirm_password),
            isLoginScreen = false,
            passwordState = confirmPasswordState,
            imeAction = ImeAction.Done,
            onImeAction = {keyboardController?.hide()}
        )
        
        Spacer(modifier = Modifier.height(30.dp))
        
        Button(
            onClick = { onButtonClicked(emailState.text, passwordState.text) },
            modifier = Modifier.fillMaxWidth(),
            shape = Shapes.medium,
            enabled = emailState.isValid && passwordState.isValid && confirmPasswordState.isValid
        ) {
           Text(text = stringResource(id = com.example.strings.R.string.sign_up)) 
        }
    }
}