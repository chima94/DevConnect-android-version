package com.example.retrofit

import com.google.gson.annotations.SerializedName

class ErrorResponse(
    @SerializedName("errors")
    var error : List<Error>
)

