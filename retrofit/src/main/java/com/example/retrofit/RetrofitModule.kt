package com.example.retrofit

import com.example.constants.Constant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson{
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(3, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson, okHttpClient: OkHttpClient): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }
}