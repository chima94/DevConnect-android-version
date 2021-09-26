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
            parentColumns = ["pk"],
            childColumns = ["account_pk"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AuthTokenEntity(

    @PrimaryKey
    @ColumnInfo
    var account_pk : Int? = -1,

    @ColumnInfo(name = "token")
    val token: String? = null
)



fun AuthTokenEntity.toAuthToken(): AuthToken{

    if(account_pk == null){
        throw Exception("Account Pk cannot be null")
    }

    if(token == null){
        throw Exception("token cannot be null")
    }
    return AuthToken(
        account_pk = account_pk.toString(),
        token = token
    )
}


fun AuthToken.toEntity(): AuthTokenEntity{
    return AuthTokenEntity(
        account_pk = account_pk.toInt(),
        token = token
    )
}