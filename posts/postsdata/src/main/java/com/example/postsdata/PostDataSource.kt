package com.example.postsdata

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.crazylegend.common.isOnline
import com.crazylegend.retrofit.throwables.NoConnectionException
import com.example.cacheauth.AuthTokenDao
import com.example.constants.Constant
import com.example.datastore.AppDataStore
import com.example.dispatchers.IoDispatcher
import com.example.networkdeveloper.DevelopersApiService
import com.example.networkresponses.developerpost.PostItem

import com.example.paging.pagedResult
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PostDataSource @AssistedInject constructor(
    private val service: DevelopersApiService,
    private val authTokenDao: AuthTokenDao,
    private val appDataStore: AppDataStore,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context
): PagingSource<Int, PostItem>(){

    @AssistedFactory
    interface PostDataSourceFactory{
        fun create(): PostDataSource
    }

    private val scope =  CoroutineScope(dispatcher)
    private var token: String = ""

    init {
        getToken()
    }

    override fun getRefreshKey(state: PagingState<Int, PostItem>): Int?  = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostItem> {
        val page = params.key ?: 1
        return if(context.isOnline){
            try {
                val data = withContext(dispatcher){
                    service.getPosts(token)
                }
                pagedResult(data, page)
            }catch (throwable : Throwable){
                LoadResult.Error(throwable)
            }
        }else{
            LoadResult.Error(NoConnectionException())
        }
    }


    private fun getToken(){
        scope.launch {
            appDataStore.readValue(Constant.PREVIOUS_AUTH_USER)?.let { email ->
                token = authTokenDao.searchByEmail(email)?.token!!
            }
        }
    }
}