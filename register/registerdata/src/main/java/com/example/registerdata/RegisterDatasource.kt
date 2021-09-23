package com.example.registerdata

import android.util.Log
import com.example.auth.DevConnectApiAuthService
import com.example.domain.AuthToken
import com.example.retrofit.handleUseCaseException
import com.example.util.DataState
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

        val hashMap = HashMap<String, String>()
        hashMap["name"] = name
        hashMap["email"] = email
        hashMap["password"] = password

        val registerResponse = service.register(hashMap)
        Log.i("REG", "token : ${registerResponse.token}")



        emit(DataState.data(data = AuthToken(token = registerResponse.token), response = null))

    }.catch {e ->

        emit(handleUseCaseException(e))
    }
}