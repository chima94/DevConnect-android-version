package com.example.auth

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideDevConnectAuthApiService(retrofitBuilder: Retrofit.Builder): DevConnectApiAuthService{
        return retrofitBuilder
            .build()
            .create(DevConnectApiAuthService::class.java)
    }

}