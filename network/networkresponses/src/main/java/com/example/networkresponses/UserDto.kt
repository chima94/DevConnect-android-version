package com.example.networkresponses

import com.example.domain.Account
import com.google.gson.annotations.SerializedName

class UserDto(

    @SerializedName("_id")
    val _id: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String
)


fun UserDto.toAccount(): Account{
    return Account(
        id = this._id,
        name = this.name,
        email = this.email,
        avatar = this.avatar,
        date = this.date
    )
}