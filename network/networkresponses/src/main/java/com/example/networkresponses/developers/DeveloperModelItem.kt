package com.example.networkresponses.developers

import com.google.gson.annotations.SerializedName

data class DeveloperModelItem(

    @SerializedName("_id")
    val _id: String,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("education")
    val education: List<Any>,
    @SerializedName("experience")
    val experience: List<Any>,
    @SerializedName("githubusername")
    val githubusername: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("skills")
    val skills: List<String>,
    @SerializedName("status")
    val status: String,
    @SerializedName("user")
    val user: User,
    @SerializedName("website")
    val website: String
)

data class Developer(
    val id: String,
    val name: String?,
    val avatar: String?,
    val company: String?
)

