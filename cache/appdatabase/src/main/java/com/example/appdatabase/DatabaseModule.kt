package com.example.appdatabase

import android.app.Application
import androidx.room.Room
import com.example.account.AccountDao
import com.example.cacheauth.AuthTokenDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase{
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun provideAccountProperties(db: AppDatabase): AccountDao{
        return db.getAccountPropertiesDao()
    }

    @Singleton
    @Provides
    fun provideAuthTokenProperties(db: AppDatabase): AuthTokenDao{
        return db.getAuthTokenPropertiesDao()
    }
}