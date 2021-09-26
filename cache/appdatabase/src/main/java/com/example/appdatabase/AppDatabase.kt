package com.example.appdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.account.AccountDao
import com.example.account.AccountEntity
import com.example.cacheauth.AuthTokenDao
import com.example.cacheauth.AuthTokenEntity


@Database(entities = [AccountEntity::class, AuthTokenEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getAccountPropertiesDao(): AccountDao
    abstract fun getAuthTokenPropertiesDao(): AuthTokenDao

    companion object{
        val DATABASE_NAME : String = "app_db"
    }
}