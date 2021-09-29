package com.example.logindata

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigator.Navigator
import com.example.registerdata.RegisterState
import com.example.session.SessionEvent
import com.example.session.SessionManager
import com.example.util.StateMessage
import com.example.util.UIComponentType
import com.example.util.doesMessageAlreadyExistInQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navigator: Navigator,
    private val loginDatasource: LoginDataSource,
    private val sessionManager: SessionManager
) : ViewModel(), Navigator by navigator{


    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state : StateFlow<LoginState> = _state


    fun login(email: String, password: String){
        loginDatasource.execute(
            email = email,
            password = password
        ).onEach {dataState ->
            _state.value = LoginState(isLoading = dataState.isLoading)

            dataState.data?.let { authToken ->
                sessionManager.onTriggerEvent(SessionEvent.Login(authToken = authToken))
            }

            dataState.stateMessage?.let { stateMessage ->
                appendToMessageQueue(stateMessage)
            }
        }.launchIn(viewModelScope)

    }


    private fun appendToMessageQueue(stateMessage: StateMessage){
        val queue = _state.value.queue
        if(!stateMessage.doesMessageAlreadyExistInQueue(queue)){
            if(!(stateMessage.response.uiComponentType is UIComponentType.None)){
                queue.add(stateMessage)
                _state.value = LoginState(queue = queue)
            }
        }
    }


    fun removeHeadFromQueue(state: LoginState) {
        try{
            val queue = state.queue
            queue.remove()
            _state.value = state.copy(queue = queue)
        }catch (e: Exception){
            Log.d("RegisterViewModel", "Nothing to remove from dialogQueue")
        }
    }

}