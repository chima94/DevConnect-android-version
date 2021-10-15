package com.example.registerdata

import com.example.account.AccountDao
import com.example.account.AccountEntity
import com.example.cacheauth.AuthTokenDao
import com.example.cacheauth.toEntity
import com.example.constants.Constant
import com.example.datastore.AppDataStore

import com.example.networkauth.DevConnectApiAuthService
import com.example.domain.AuthToken
import com.example.retrofit.handleUseCaseException
import com.example.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


class RegisterDatasource(
    private val service: DevConnectApiAuthService,
    private val accountDao: AccountDao,
    private val authTokenDao: AuthTokenDao,
    private val appDataStore: AppDataStore
) {


    fun execute(
        name: String,
        email: String,
        password: String
    ): Flow<DataState<AuthToken>> = flow {


        emit(DataState.loading())

        val hashMap = HashMap<String, String>()
        hashMap["name"] = name
        hashMap["email"] = email
        hashMap["password"] = password

        val registerResponse = service.register(hashMap)
        val user = service.user(registerResponse.token)

        //cache account details
        accountDao.insertAndReplace(
            AccountEntity(
                id = user.id,
                email = user.email,
                avatar = user.avatar,
                date = user.date,
                name = user.name
            )
        )


        //cache auth token
        val authToken =
            AuthToken(
                id = user.id,
                account_email = email,
                token = registerResponse.token
            )
        val result = authTokenDao.insert(authToken.toEntity())
        if(result < 0){
            throw Exception(Constant.ERROR_SAVE_AUTH_TOKEN)
        }

        //save authenticated user to datastore for auto-login next time
        appDataStore.setValue(Constant.PREVIOUS_AUTH_USER, email)
        emit(DataState.data(data = authToken, response = null))

    }.catch {e ->
        emit(handleUseCaseException(e))
    }
}