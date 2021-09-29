package com.example.networkauth

import com.example.networkresponses.RegistrationResponses
import com.example.networkresponses.UserDto
import retrofit2.http.*

interface DevConnectApiAuthService {


    @POST("users")
    suspend fun register(@Body hashMap: HashMap<String, String>): RegistrationResponses

    @GET("auth")
    suspend fun user(
        @Header("X-Auth-Token") authorization: String
    ): UserDto
}