package com.example.registerdata

import com.example.account.AccountDao
import com.example.account.AccountEntity

class AccountDaoFake(
    private val db: AppDatabaseFake
) :AccountDao{

    override suspend fun searchByEmail(email: String): AccountEntity? {
        for(account in db.accounts){
            if(account.email == email){
                return account
            }
        }
        return null
    }



    override suspend fun insertAndReplace(accountEntity: AccountEntity): Long {
        db.accounts.removeIf {
            it.email == accountEntity.email
        }
        db.accounts.add(accountEntity)
        return 1
    }

    override suspend fun insertOrIgnore(accountEntity: AccountEntity): Long {
        if(!db.accounts.contains(accountEntity)){
            db.accounts.add(accountEntity)
        }
        return 1
    }
}