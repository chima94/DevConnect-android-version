package com.example.retrofit

import com.example.constants.Constant
import com.example.util.DataState
import com.example.util.MessageType
import com.example.util.Response
import com.example.util.UIComponentType
import retrofit2.HttpException



fun <T> handleUseCaseException(e: Throwable): DataState<T>{
    e.printStackTrace()
    when(e){
        is HttpException ->{
            val errorResponse = convertErrorBody(e)
            return DataState.error<T>(
                response = Response(
                    message = errorResponse,
                    uiComponentType = UIComponentType.Dialog(),
                    messageType = MessageType.Error()
                )
            )
        }
        else ->{
            return DataState.error<T>(
                response = Response(
                    message = e.message,
                    uiComponentType = UIComponentType.Dialog(),
                    messageType = MessageType.Error()
                )
            )
        }
    }
}



private fun convertErrorBody(throwable: HttpException) : String?{
    return try{
        throwable.response()?.errorBody()?.toString()
    }catch (exception: Exception){
        Constant.INVALID_CREDENTIALS
    }
}