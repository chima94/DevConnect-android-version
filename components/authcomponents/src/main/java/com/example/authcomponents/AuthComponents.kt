package com.example.authcomponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.errorcomponent.TextFieldError
import com.example.util.EmailState
import com.example.util.NameState
import com.example.util.TextFieldState


@Composable
fun SignInSignUpScreen(
    modifier: Modifier = Modifier,
    content: @Composable() () -> Unit
){
    LazyColumn(modifier = modifier){
        item{
            Spacer(modifier = Modifier.height(44.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ){
                content()
            }
            Spacer(modifier = modifier.height(16.dp))
        }
    }
}





@Composable
fun SignInSignUpTopAppBar(
    topAppBarText: String,
    onBackPressed: () -> Unit
){
    TopAppBar(
        title = {
            Text(
                text = topAppBarText,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {onBackPressed()}
            ) {
                Icon(
                    imageVector = Icons.Filled.ChevronLeft,
                    contentDescription = stringResource(id = com.example.strings.R.string.back)
                )
            }
        },
        actions = {
            Spacer(modifier = Modifier.width(68.dp))
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        elevation = 0.dp
    )
}


@Composable
fun Email(
    modifier: Modifier = Modifier,
    isLoginScreen: Boolean = true,
    emailState: TextFieldState = remember{EmailState()},
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
){
    
    OutlinedTextField(
        value = emailState.text,
        onValueChange = {emailState.text = it},
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = stringResource(com.example.strings.R.string.email),
                    style = MaterialTheme.typography.body1
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                emailState.onFocusedChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    emailState.enableShowErrors()
                }
            },
        isError = emailState.showErrors(),
        textStyle = MaterialTheme.typography.body2,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onNext = {onImeAction()})
    )
    if(!isLoginScreen){
        emailState.getError()?.let { error -> TextFieldError(textError = error) }
    }

}




@Composable
fun Name(
    nameState: TextFieldState = remember{NameState()},
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
){

    OutlinedTextField(
        value = nameState.text,
        onValueChange = {nameState.text = it},
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = stringResource(com.example.strings.R.string.name),
                    style = MaterialTheme.typography.body1
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                nameState.onFocusedChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    nameState.enableShowErrors()
                }
            },
        isError = nameState.showErrors(),
        textStyle = MaterialTheme.typography.body2,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onNext = {onImeAction()})
    )

    nameState.getError()?.let { error -> TextFieldError(textError = error) }


}




@Composable
fun Password(
    modifier: Modifier = Modifier,
    isLoginScreen: Boolean = true,
    label : String,
    passwordState: TextFieldState,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {}
){
    var showPassword by remember{ mutableStateOf(false) }

    OutlinedTextField(
        value = passwordState.text,
        onValueChange = {passwordState.text = it},
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                passwordState.onFocusedChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    passwordState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.body2,
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2
                )
            }
        },
        trailingIcon = {
            if(showPassword){
                IconButton(
                    onClick = { showPassword = false}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = stringResource(com.example.strings.R.string.hide_password)
                    )
                }
            }else{
                IconButton(
                    onClick = { showPassword = true }
                ) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = stringResource(com.example.strings.R.string.show_password)
                    )
                }
            }
        },
        visualTransformation = if(showPassword){
            VisualTransformation.None
        }else{
            PasswordVisualTransformation()
        },
        isError = passwordState.showErrors(),
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = when(imeAction){
            ImeAction.Next -> KeyboardActions(onNext = {onImeAction()})
            ImeAction.Done -> KeyboardActions(onDone = {onImeAction()})
            else -> KeyboardActions(onDone = {onImeAction()})
        }
    )
    if(!isLoginScreen){
        passwordState.getError()?.let {error-> TextFieldError(textError = error) }
    }
}