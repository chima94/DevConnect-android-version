package com.example.registerdata

import com.example.util.Queue
import com.example.util.StateMessage

data class RegisterState(
    val isLoading: Boolean = false,
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val queue: Queue<StateMessage> = Queue(mutableListOf())
)