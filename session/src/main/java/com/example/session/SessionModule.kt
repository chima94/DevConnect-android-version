package com.example.session

import com.example.account.AccountDao
import com.example.cacheauth.AuthTokenDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {

    @Singleton
    @Provides
    fun checkPreviousAuthUser(
        accountDao: AccountDao,
        authTokenDao: AuthTokenDao
    ): CheckPreviousAuthUser{
        return CheckPreviousAuthUser(accountDao = accountDao, authTokenDao = authTokenDao)
    }
}