package com.example.retrofit

import android.util.Log
import com.example.constants.Constant
import com.example.util.DataState
import com.example.util.MessageType
import com.example.util.Response
import com.example.util.UIComponentType
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
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
        val errorResponse = Gson()
            .fromJson(throwable.response()?.errorBody()?.charStream(),
                ErrorResponse::class.java)
        errorResponse.error[0].msg
    }catch (exception: Exception){
        Constant.INVALID_CREDENTIALS
    }
}




