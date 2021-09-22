package com.example.statemessagecomponent

import android.app.AlertDialog
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toaster.ToasterViewModel
import com.example.util.*

@Composable
fun ProcessQueue(
    queue: Queue<StateMessage>,
    stateMessageCallback: () -> Unit
){

    if(!queue.isEmpty()){
        queue.peek()?.let { stateMessage ->
            OnResponseReceived(
                response = stateMessage.response,
                stateMessageCallback = stateMessageCallback
            )
        }
    }
}


@Composable
private fun OnResponseReceived(
    response: Response,
    stateMessageCallback: () -> Unit
){
    when(response.uiComponentType){
        is UIComponentType.AreYouSureDialog ->{

        }

        is UIComponentType.Dialog->{
            DisplayDialog(
                response = response,
                stateMessageCallback = stateMessageCallback
            )
        }

        is UIComponentType.Toast->{
            DisplayToast(
                message = response.message,
                stateMessageCallback = stateMessageCallback
            )
        }

        is UIComponentType.None->{

        }
    }
}



@Composable
private fun DisplayDialog(
    response: Response,
    stateMessageCallback: () -> Unit
){
    response.message?.let { message ->

        when(response.messageType){

            is MessageType.Error->{

                DisplayErrorDialog(
                    message = response.message,
                    stateMessageCallback = stateMessageCallback
                )
            }

            is MessageType.Success->{}

            is MessageType.Info->{}

            else ->{}
        }
    }
}

@Composable
private fun DisplaySuccessDialog(
    message: String?,
    stateMessageCallback: () -> Unit
){

}


@Composable
private fun DisplayErrorDialog(
    message: String?,
    stateMessageCallback: () -> Unit
){
    Dialog(
        title = stringResource(com.example.strings.R.string.text_error),
        message = message,
        confirmText = stringResource(com.example.strings.R.string.ok),
        onDismiss = stateMessageCallback,
        onConfirm = stateMessageCallback
    )
}

@Composable
private fun DisplayInfoDialog(
    message: String?,
    stateMessageCallback: () -> Unit
){

}



@Composable
private fun AreSureDialog(
    message: String,
    stateMessageCallback: () -> Unit
){

}



@Composable
private fun DisplayToast(
    message: String?,
    stateMessageCallback: () -> Unit
){
    val toasterViewModel: ToasterViewModel = hiltViewModel()

    message?.let { msg->
        toasterViewModel.shortToast(msg)
        stateMessageCallback()
    }
}


@Composable
private fun Dialog(
    title: String? = null,
    message: String? = null,
    dismissText: String? = null,
    onDismiss : () -> Unit = {},
    confirmText: String? = null,
    onConfirm: () -> Unit
){
    var openDialog by remember{ mutableStateOf(!message.isNullOrBlank())}

    if(openDialog){
        AlertDialog(
            title = {
                title?.let {
                    Text(text = title)
                }
            },

            onDismissRequest = {
                onDismiss()
                openDialog = false
            },

            dismissButton = {
                dismissText?.let {dismissText->
                    Button(
                        onClick = {
                            onDismiss()
                            openDialog = false
                        },
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .padding(bottom = 8.dp)
                    ){
                        Text(text = dismissText)
                    }
                }

            },

            confirmButton = {
                confirmText?.let {confirmText->
                    Button(
                        onClick = {
                            onConfirm()
                            openDialog = false
                        },
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .padding(bottom = 8.dp)
                    ) {
                        Text(text = confirmText)
                    }
                }
            },

            text = {
                message?.let {
                    Text(text = message)
                }
            }
        )
    }

}