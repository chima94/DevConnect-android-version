package com.example.logindata

import com.example.account.AccountDao
import com.example.account.toEntity
import com.example.cacheauth.AuthTokenDao
import com.example.cacheauth.toEntity
import com.example.constants.Constant
import com.example.datastore.AppDataStore
import com.example.domain.Account
import com.example.domain.AuthToken
import com.example.networkauth.DevConnectApiAuthService
import com.example.retrofit.handleUseCaseException
import com.example.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class LoginDataSource(
    private val service: DevConnectApiAuthService,
    private val accountDao: AccountDao,
    private val authTokenDao: AuthTokenDao,
    private val appDataStoreManager: AppDataStore
) {


    fun execute(
        email: String,
        password: String
    ): Flow<DataState<AuthToken>> = flow {

        emit(DataState.loading())

        val hashMap = HashMap<String, String>()
        hashMap["email"] = email
        hashMap["password"] = password

        val loginResponse = service.login(hashMap)

        accountDao.insertOrIgnore(
            Account(
                email = email,
                name = ""
            ).toEntity()
        )

        val authToken = AuthToken(
            account_email = email,
            token = loginResponse.token
        )

        val result = authTokenDao.insert(authToken = authToken.toEntity())

        if(result < 0){
            throw Exception(Constant.ERROR_SAVE_AUTH_TOKEN)
        }

        appDataStoreManager.setValue(Constant.PREVIOUS_AUTH_USER, email)
        emit(DataState.data(data = authToken, response = null))
    }.catch { e->
        emit(handleUseCaseException(e))
    }
}