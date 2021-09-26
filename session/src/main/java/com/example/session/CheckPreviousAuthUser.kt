package com.example.session

import com.example.account.AccountDao
import com.example.cacheauth.AuthTokenDao
import com.example.cacheauth.toAuthToken
import com.example.constants.Constant
import com.example.domain.AuthToken
import com.example.util.DataState
import com.example.util.MessageType
import com.example.util.Response
import com.example.util.UIComponentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


class CheckPreviousAuthUser(
    private val accountDao: AccountDao,
    private val authTokenDao: AuthTokenDao
) {

    fun execute(
        email: String
    ): Flow<DataState<AuthToken>> = flow {

        emit(DataState.loading())

        var authToken: AuthToken? = null
        val entity = accountDao.searchByEmail(email)
        if(entity != null){
            authToken = authTokenDao.searchByPk(entity.pk)?.toAuthToken()
            if(authToken != null){
                emit(DataState.data(response = null, data = authToken))
            }
        }

        if(authToken == null){
            throw Exception(Constant.ERROR_NO_PREVIOUS_AUTH_USER)
        }
    }.catch{ e ->
        e.printStackTrace()
        emit(returnNoPreviousAuthUser())
    }


    private fun returnNoPreviousAuthUser(): DataState<AuthToken>{
        return DataState.error(
            response = Response(
                message = Constant.RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE,
                UIComponentType.None(),
                MessageType.Error()
            )
        )
    }
}