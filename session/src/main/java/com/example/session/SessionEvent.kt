package com.example.session

import com.example.domain.AuthToken

sealed class SessionEvent{

    object Logout: SessionEvent()

    data class Login(
        val authToken: AuthToken
    ): SessionEvent()

    data class CheckPreviousAuthUser(
        val email: String
    ): SessionEvent()

    object OnRemoveHeadFromQueue: SessionEvent()
}
