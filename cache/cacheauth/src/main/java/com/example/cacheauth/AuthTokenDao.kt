package com.example.cacheauth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthTokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(authToken: AuthTokenEntity): Long

    @Query("DELETE FROM auto_token")
    suspend fun clearTokens()

    @Query("SELECT * FROM auto_token WHERE account_pk = :pk")
    suspend fun searchByPk(pk: Int): AuthTokenEntity?
}