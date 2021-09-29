package com.example.logindata

import com.example.util.Queue
import com.example.util.StateMessage

data class LoginState(
    val isLoading: Boolean = false,
    val token: String = "",
    val queue: Queue<StateMessage> = Queue(mutableListOf())
)
