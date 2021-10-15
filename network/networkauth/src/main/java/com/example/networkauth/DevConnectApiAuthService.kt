package com.example.networkauth

import com.example.networkresponses.auth.AuthResponse
import com.example.networkresponses.auth.UserDto
import retrofit2.http.*

interface DevConnectApiAuthService {


    @POST("users")
    suspend fun register(@Body hashMap: HashMap<String, String>): AuthResponse

    @GET("auth")
    suspend fun user(
        @Header("X-Auth-Token") authorization: String
    ): UserDto

    @POST("auth")
    suspend fun login(@Body hashMap: HashMap<String, String>): AuthResponse
}