package com.example.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.Account

@Entity(tableName = "account_properties")
data class AccountEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "email")
    val email : String,


    @ColumnInfo(name = "name")
    val name: String,
)


fun AccountEntity.toAccount(): Account{
    return Account(
        name = name,
        email = email
    )
}


fun Account.toEntity(): AccountEntity{
    return AccountEntity(
        name = name,
        email = email,
    )
}