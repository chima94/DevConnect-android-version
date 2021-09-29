package com.example.registerdata

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
object RegisterModule {


    @Singleton
    @Provides
    fun provideRegister(
        service: DevConnectApiAuthService,
        accountDao: AccountDao,
        authTokenDao: AuthTokenDao,
        appDataStore: AppDataStore
    ):RegisterDatasource{
        return RegisterDatasource(service, accountDao, authTokenDao, appDataStore)
    }
}