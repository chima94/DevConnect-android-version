package com.example.registerdata

import com.example.cacheauth.AuthTokenDao
import com.example.cacheauth.AuthTokenEntity

class AuthTokenDaoFake(
    private val db: AppDatabaseFake
) : AuthTokenDao{

    override suspend fun insert(authToken: AuthTokenEntity): Long {
       db.authTokens.add(authToken)
        return 1
    }

    override suspend fun clearTokens() {
        db.authTokens.clear()
    }


    override suspend fun searchByEmail(email: String): AuthTokenEntity? {
        for (entity in db.authTokens){
            if(entity.account_email == email){
                return entity
            }
        }

        return null
    }


}