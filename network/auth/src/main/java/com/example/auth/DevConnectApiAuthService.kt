package com.example.auth

import retrofit2.http.*

interface DevConnectApiAuthService {


    @POST("users")
    suspend fun register(@Body hashMap: HashMap<String, String>): RegistrationResponses
}