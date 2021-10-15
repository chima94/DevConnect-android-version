package com.example.postsdata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.cacheauth.AuthTokenDao
import com.example.constants.Constant
import com.example.datastore.AppDataStore
import com.example.dispatchers.IoDispatcher
import com.example.paging.data.PagingDataProvider
import com.example.paging.data.PagingDataSourceHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postDataSourceFactory: PostDataSource.PostDataSourceFactory,
    override val savedStateHandle: SavedStateHandle,
    application: Application,
    private val dataProvider: PagingDataProvider,
    private val authTokenDao: AuthTokenDao,
    private val appDataStore: AppDataStore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AndroidViewModel(application), PagingDataSourceHandle {

    var userId: String? = null

    private  val postDataSource
        get() = postDataSourceFactory.create()

    val postsData =  dataProvider.providePageData(viewModelScope, ioDispatcher){
        postDataSource
    }

    init {
        getUserId()
    }


    private fun getUserId(){
        viewModelScope.launch {
            appDataStore.readValue(Constant.PREVIOUS_AUTH_USER)?.let { email ->
                userId = authTokenDao.searchByEmail(email)?.id
            }
        }
    }
}
