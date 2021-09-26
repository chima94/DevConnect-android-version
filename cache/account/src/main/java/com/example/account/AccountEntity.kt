package com.example.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.Account

@Entity(tableName = "account_properties")
data class AccountEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pk")
    val pk: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "email")
    val email : String,

    @ColumnInfo(name = "avatar")
    val avatar: String,

    @ColumnInfo(name = "date")
    val date: String
)

fun AccountEntity.toAccount(): Account{
    return Account(
        id = pk.toString(),
        name = name,
        email = email,
        avatar = avatar,
        date = date
    )
}


fun Account.toEntity(): AccountEntity{
    return AccountEntity(
        pk = id.toInt(),
        name = name,
        email = email,
        avatar = avatar,
        date = date
    )
}