package com.example.session

import com.example.datastore.AppDataStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val checkPreviousAuthUser: CheckPreviousAuthUser,
    private val appDataStoreManager: AppDataStore
){

}