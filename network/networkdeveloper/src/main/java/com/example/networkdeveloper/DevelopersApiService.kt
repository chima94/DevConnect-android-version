package com.example.networkdeveloper


import com.example.networkresponses.developerreponse.DeveloperModel
import retrofit2.http.GET
import retrofit2.http.Header

interface DevelopersApiService {

    @GET("profiles")
    suspend fun getDevelopersProfile(
        @Header("X-Auth-Token") authorization: String
    ) : DeveloperModel
}