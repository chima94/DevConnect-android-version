package com.example.networkresponses.developerpost

import com.google.gson.annotations.SerializedName

data class PostItem(
    @SerializedName("_id")
    val id: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("comments")
    val comments: List<Comment>,
    @SerializedName("commets")
    val commets: List<Any>,
    @SerializedName("date")
    val date: String,
    @SerializedName("likes")
    val likes: List<Like>,
    @SerializedName("name")
    val name: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("user")
    val user: String
)





