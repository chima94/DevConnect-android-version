package com.example.developersdata

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.cacheauth.AuthTokenDao
import com.example.constants.Constant
import com.example.datastore.AppDataStore
import com.example.dispatchers.IoDispatcher
import com.example.networkdeveloper.DevelopersApiService
import com.example.networkresponses.developerreponse.DeveloperModelItem
import com.example.paging.data.PagingDataProvider
import com.example.paging.data.PagingDataSourceHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiNjE1NDI2NmQ4YTA3MDAwMDE2Zjg0YmY2In0sImlhdCI6MTYzMzMwNjEyOSwiZXhwIjoxNjMzNjY2MTI5fQ.6Si1acITcc-zgYTPmJkTY7AD2KTUW17Fl6p_m0y86R4"
@HiltViewModel
class DeveloperViewModel @Inject constructor(
    private val developerDataSourceFactory: DeveloperDataSource.DeveloperDataSourceFactory,
    override val savedStateHandle: SavedStateHandle,
    application: Application,
    private val dataProvider: PagingDataProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AndroidViewModel(application), PagingDataSourceHandle{

    private  val developerDataSource
        get() = developerDataSourceFactory.create()

    val developerData =  dataProvider.providePageData(viewModelScope, ioDispatcher){
        developerDataSource
    }



}


