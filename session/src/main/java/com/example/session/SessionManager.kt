package com.example.session

import android.util.Log
import com.example.constants.Constant
import com.example.datastore.AppDataStore
import com.example.domain.AuthToken
import com.example.util.StateMessage
import com.example.util.UIComponentType
import com.example.util.doesMessageAlreadyExistInQueue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val checkPreviousAuthUser: CheckPreviousAuthUser,
    private val appDataStoreManager: AppDataStore
){


    private val sessionScope = CoroutineScope(Main)

    private val _state: MutableStateFlow<SessionState> = MutableStateFlow(SessionState())
    val state : StateFlow<SessionState> = _state

    init {
        sessionScope.launch {
            appDataStoreManager.readValue(Constant.PREVIOUS_AUTH_USER)?.let {email ->
                onTriggerEvent(SessionEvent.CheckPreviousAuthUser(email = email))
            }?: onFinishCheckingPreviousAuthUser()
        }
    }


    fun onTriggerEvent(event: SessionEvent){

        when(event){
            is SessionEvent.Login ->{
                login(event.authToken)
            }
            is SessionEvent.CheckPreviousAuthUser ->{
                checkPreviousAuthUser(email = event.email)
            }
            is SessionEvent.Logout ->{}
            is SessionEvent.OnRemoveHeadFromQueue-> {
                removeHeadFromQueue()
            }
        }
    }


    private fun checkPreviousAuthUser(email : String){
        checkPreviousAuthUser.execute(email).onEach {dataState ->
            _state.value = SessionState(isLoading = true)
            dataState.data?.let {authToken ->
                _state.value = SessionState(authToken = authToken)
                onTriggerEvent(SessionEvent.Login(authToken))
            }


            dataState.stateMessage?.let { stateMessage ->
                if(stateMessage.response.message.equals(Constant.RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE)){
                    onFinishCheckingPreviousAuthUser()
                }else{
                    appendToMessageQueue(stateMessage = stateMessage)
                }
            }
        }.launchIn(sessionScope)
    }



    private fun login(authToken: AuthToken){
        _state.value = SessionState(authToken = authToken)
    }




    private fun logout(){

    }


    private fun appendToMessageQueue(stateMessage: StateMessage){
        val queue = _state.value.queue
        if(!stateMessage.doesMessageAlreadyExistInQueue(queue)){
            if(!(stateMessage.response.uiComponentType is UIComponentType.None)){
                queue.add(stateMessage)
                _state.value = SessionState(queue = queue)
            }
        }

    }


    private fun removeHeadFromQueue(){
        try {
            val queue = _state.value.queue
            queue.remove()
            _state.value = SessionState(queue = queue)
        }catch (e: Exception){
            Log.d("SESSION", "Nothing to remove from the DialogueQueue")
        }
    }

    private fun onFinishCheckingPreviousAuthUser(){
        _state.value = SessionState(didCheckForPreviousAuthUser = true)
    }


    private fun clearAuthUser(){
        sessionScope.launch {
            appDataStoreManager.setValue(Constant.PREVIOUS_AUTH_USER, "")
        }
    }
}