package com.example.session

import com.example.domain.AuthToken
import com.example.util.Queue
import com.example.util.StateMessage
import java.util.*

data class SessionState(
    val isLoading: Boolean = false,
    val authToken: AuthToken? = null,
    val didCheckForPreviousAuthUser: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf())
)