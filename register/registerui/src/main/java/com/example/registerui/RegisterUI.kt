package com.example.registerui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.example.authcomponents.*
import com.example.composeextension.supportWideScreen
import com.example.registerdata.RegisterState

import com.example.registerdata.RegisterViewModel
import com.example.shape.Shapes
import com.example.statemessagecomponent.ProcessQueue
import com.example.toaster.ToasterViewModel
import com.example.util.ConfirmPasswordState
import com.example.util.EmailState
import com.example.util.NameState
import com.example.util.PasswordState

@Composable
fun Register(){

    val scope = rememberCoroutineScope()
    val registerViewModel: RegisterViewModel = hiltViewModel()
    val toasterViewModel: ToasterViewModel = hiltViewModel()

    val lifecycleOwner = LocalLifecycleOwner.current
    val stateFlowLifecycleAware = remember(registerViewModel.state, lifecycleOwner){
        registerViewModel.state.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    val state by stateFlowLifecycleAware.collectAsState(initial = RegisterState())


    ProcessQueue(
        queue = state.queue,
        stateMessageCallback = { registerViewModel.removeHeadFromQueue(state) }
    )



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
                        state = state
                    ) { name, email, password ->
                        registerViewModel.register(
                            name = name,
                            email = email,
                            password = password
                        )
                    }
                }
            }
        }
    )

    /*Box(modifier = Modifier.fillMaxSize()){
        ErrorSnackbar(
            snackbarHostState = snackbarHostState,
            onDismiss = {snackbarHostState.currentSnackbarData?.dismiss()},
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }*/
}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpContent(
    state: RegisterState,
    onButtonClicked: (name: String, email: String, password: String) -> Unit
){

    val nameState = remember{NameState()}
    val emailState = remember{ EmailState() }
    val emailRequester = remember{FocusRequester()}
    val passwordFocusRequester = remember { FocusRequester()}
    val passwordState = remember{ PasswordState() }
    val confirmPasswordState = remember{ ConfirmPasswordState(passwordState = passwordState)}
    val confirmPasswordFocusRequester = remember{ FocusRequester()}
    val keyboardController = LocalSoftwareKeyboardController.current
    
    Column(modifier = Modifier.fillMaxWidth()) {

        Name(
            nameState = nameState,
            onImeAction = {emailRequester.requestFocus()}
        )

        Spacer(modifier = Modifier.height(16.dp))

        Email(
            modifier = Modifier.focusRequester(emailRequester),
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
            onClick = { onButtonClicked(nameState.text, emailState.text, passwordState.text) },
            modifier = Modifier.fillMaxWidth(),
            shape = Shapes.medium,
            enabled = nameState.isValid && emailState.isValid &&
                    passwordState.isValid && confirmPasswordState.isValid && !state.isLoading
        ) {
           Text(text = stringResource(id = com.example.strings.R.string.sign_up)) 
        }

        Spacer(modifier = Modifier.height(10.dp))
        if(state.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}