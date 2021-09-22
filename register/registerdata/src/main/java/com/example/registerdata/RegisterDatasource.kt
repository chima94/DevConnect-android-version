package com.example.registerdata

import android.util.Log
import com.example.auth.DevConnectApiAuthService
import com.example.domain.AuthToken
import com.example.retrofit.handleUseCaseException
import com.example.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RegisterDatasource(
    private val service: DevConnectApiAuthService
) {



    fun execute(
        name: String,
        email: String,
        password: String
    ): Flow<DataState<AuthToken>> = flow {
        emit(DataState.loading())
        delay(5000)

        if(name.isNotEmpty()){
            throw Exception("something went wrong")
        }

        emit(DataState.data(data = AuthToken(token = "token incoming"), response = null))

    }.catch {e ->
        Log.i("REGISTER", "error")
        emit(handleUseCaseException(e))
    }
}