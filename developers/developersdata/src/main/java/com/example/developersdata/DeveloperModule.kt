package com.example.developersdata

import com.example.networkdeveloper.DevelopersApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeveloperModule{


    /*@Provides
    @Singleton
    fun provideDeveloperDatasource(
        service: DevelopersApiService
    ): DeveloperDataSource{
        return DeveloperDataSource(service)
    }*/
}