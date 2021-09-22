package com.example.auth

import com.google.gson.annotations.SerializedName

class RegistrationResponses (

    @SerializedName("token")
    var token: String,

    @SerializedName("errors")
    var errors: List<Error>
    )