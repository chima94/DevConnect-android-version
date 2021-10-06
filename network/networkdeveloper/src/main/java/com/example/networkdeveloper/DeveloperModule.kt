package com.example.networkdeveloper

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeveloperModule {

    @Singleton
    @Provides
    fun provideDevConnectAuthApiService(retrofitBuilder: Retrofit.Builder): DevelopersApiService {
        return retrofitBuilder
            .build()
            .create(DevelopersApiService::class.java)
    }
}