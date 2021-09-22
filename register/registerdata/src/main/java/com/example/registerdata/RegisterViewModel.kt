package com.example.registerdata

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigator.Navigator
import com.example.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val navigator: Navigator,
    private val registerDatasource: RegisterDatasource
) : ViewModel(), Navigator by navigator{


    private val _state: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState())
    val state : StateFlow<RegisterState> = _state


    fun register(name: String, email: String, password: String){
        registerDatasource.execute(
            name = name,
            email = email,
            password = password
        ).onEach {dataState ->
            _state.value = RegisterState(isLoading = dataState.isLoading)

            dataState.data?.let { authToken ->
                _state.value = RegisterState(email = authToken.token)
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
                _state.value = RegisterState(queue = queue)
            }
        }
    }


    fun removeHeadFromQueue(state: RegisterState) {
        try{
            val queue = state.queue
            queue.remove()
            _state.value = state.copy(queue = queue)
        }catch (e: Exception){
            Log.d("RegisterViewModel", "Nothing to remove from dialogQueue")
        }
    }
}