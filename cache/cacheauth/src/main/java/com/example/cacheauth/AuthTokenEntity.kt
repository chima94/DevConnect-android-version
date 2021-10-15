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
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AuthTokenEntity(

    @PrimaryKey
    @ColumnInfo
    var id: String,

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
        id = id,
        account_email = account_email,
        token = token
    )
}


fun AuthToken.toEntity(): AuthTokenEntity{
    return AuthTokenEntity(
        id = id,
        account_email = account_email,
        token = token
    )
}