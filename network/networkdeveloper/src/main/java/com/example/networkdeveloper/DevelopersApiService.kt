package com.example.networkdeveloper


import com.example.networkresponses.developerpost.Post
import com.example.networkresponses.developers.DeveloperModel
import retrofit2.http.GET
import retrofit2.http.Header

interface DevelopersApiService {

    @GET("profiles")
    suspend fun getDevelopersProfile(
        @Header("X-Auth-Token") authorization: String
    ) : DeveloperModel


    @GET("posts")
    suspend fun getPosts(
        @Header("X-Auth-Token") authorization: String
    ): Post
}