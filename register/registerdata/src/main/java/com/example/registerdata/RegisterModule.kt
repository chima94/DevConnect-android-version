package com.example.registerdata

import com.example.auth.DevConnectApiAuthService
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
        service: DevConnectApiAuthService
    ):RegisterDatasource{
        return RegisterDatasource(service)
    }
}