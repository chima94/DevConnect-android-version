package com.example.logindata

import com.example.account.AccountDao
import com.example.cacheauth.AuthTokenDao
import com.example.datastore.AppDataStore
import com.example.networkauth.DevConnectApiAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Singleton
    @Provides
    fun provideLogin(
        service: DevConnectApiAuthService,
        accountDao: AccountDao,
        authTokenDao: AuthTokenDao,
        appDataStore: AppDataStore
    ): LoginDataSource{
        return LoginDataSource(
            service = service,
            accountDao = accountDao,
            authTokenDao = authTokenDao,
            appDataStoreManager = appDataStore
        )
    }
}