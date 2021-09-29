package com.example.networkresponses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthResponse (

    @SerializedName("token")
    var token: String,
)