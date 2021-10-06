package com.example.developermodel

data class DeveloperModelItem(

    val __v: Int,
    val _id: String,
    val bio: String,
    val company: String,
    val date: String,
    val education: List<Any>,
    val experience: List<Any>,
    val githubusername: String,
    val location: String,
    val skills: List<String>,
    val status: String,
    val user: User,
    val website: String
)