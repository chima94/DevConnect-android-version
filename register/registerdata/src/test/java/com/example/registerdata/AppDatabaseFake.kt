package com.example.registerdata

import com.example.account.AccountEntity
import com.example.cacheauth.AuthTokenEntity
import com.example.domain.AuthToken

class AppDatabaseFake {

    val accounts = mutableListOf<AccountEntity>()
    val authTokens = mutableListOf<AuthTokenEntity>()
}