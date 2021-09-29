package com.example.cacheauth

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.account.AccountEntity
import com.example.domain.AuthToken


@Entity(
    tableName = "auto_token",
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["email"],
            childColumns = ["account_email"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AuthTokenEntity(

    @PrimaryKey
    @ColumnInfo
    var account_email : String = "",

    @ColumnInfo(name = "token")
    val token: String? = null
)



fun AuthTokenEntity.toAuthToken(): AuthToken{
    if(token == null){
        throw Exception("token cannot be null")
    }
    return AuthToken(
        account_email = account_email,
        token = token
    )
}


fun AuthToken.toEntity(): AuthTokenEntity{
    return AuthTokenEntity(
        account_email = account_email,
        token = token
    )
}